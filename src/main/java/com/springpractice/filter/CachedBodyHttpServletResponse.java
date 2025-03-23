package com.springpractice.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream cachedContent;
    private CachedBodyServletOutputStream cachedOutputStream;
    private PrintWriter writer;
    private boolean outputStreamUsed = false;
    private boolean writerUsed = false;
    private Charset characterEncoding = StandardCharsets.UTF_8;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        this.cachedContent = new ByteArrayOutputStream(1024); // 초기 버퍼 크기 지정
    }

    @Override
    public void setCharacterEncoding(String charset) {
        super.setCharacterEncoding(charset);
        this.characterEncoding = Charset.forName(charset);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writerUsed) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }
        outputStreamUsed = true;
        if (this.cachedOutputStream == null) {
            this.cachedOutputStream = new CachedBodyServletOutputStream(cachedContent);
        }
        return this.cachedOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStreamUsed) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }
        writerUsed = true;
        if (this.writer == null) {
            this.writer = new PrintWriter(new OutputStreamWriter(cachedContent, characterEncoding), true);
        }
        return this.writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (cachedOutputStream != null) {
            cachedOutputStream.flush();
        }
        super.flushBuffer();
    }

    @Override
    public void reset() {
        super.reset();
        cachedContent.reset();
        outputStreamUsed = false;
        writerUsed = false;
        cachedOutputStream = null;
        writer = null;
    }

    @Override
    public void resetBuffer() {
        super.resetBuffer();
        cachedContent.reset();
        if (cachedOutputStream != null) {
            try {
                cachedOutputStream.flush();
            } catch (IOException e) {
                throw new RuntimeException("Failed to flush the cached output stream", e);
            }
        }
        if (writer != null) {
            writer.flush();
        }
    }

    /**
     * 캐시된 응답 본문 내용을 바이트 배열로 반환합니다.
     * @return 캐시된 응답 본문 내용
     */
    public byte[] getCachedContent() {
        flushBuffersIfNeeded();
        return cachedContent.toByteArray();
    }

    /**
     * 캐시된 응답 본문 내용을 문자열로 반환합니다.
     * @return 캐시된 응답 본문 내용 (문자열)
     */
    public String getContentAsString() {
        flushBuffersIfNeeded();
        return new String(cachedContent.toByteArray(), characterEncoding);
    }

    /**
     * 캐시된 응답 본문을 원래 응답으로 복사합니다.
     * 필터 체인 완료 후 이 메서드를 호출해야 합니다.
     * @throws IOException I/O 오류가 발생한 경우
     */
    public void copyBodyToResponse() throws IOException {
        if (cachedContent.size() == 0) {
            return; // 내용이 없으면 복사하지 않음
        }

        flushBuffersIfNeeded();

        // 응답이 이미 커밋되었는지 확인
        if (!getResponse().isCommitted()) {
            // Content-Length 헤더 설정
            getResponse().setContentLength(cachedContent.size());
        }

        // 원래 응답으로 내용 복사
        ServletOutputStream originalOutputStream = getResponse().getOutputStream();
        cachedContent.writeTo(originalOutputStream);
        originalOutputStream.flush();
    }

    /**
     * 필요한 경우 버퍼를 플러시합니다.
     */
    private void flushBuffersIfNeeded() {
        try {
            if (writer != null) {
                writer.flush();
            } else if (cachedOutputStream != null) {
                cachedOutputStream.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to flush buffers", e);
        }
    }

    /**
     * ServletOutputStream의 구현 클래스로, 모든 출력을 ByteArrayOutputStream에 캐시합니다.
     */
    private static class CachedBodyServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream cachedContent;

        public CachedBodyServletOutputStream(ByteArrayOutputStream cachedContent) {
            this.cachedContent = cachedContent;
        }

        @Override
        public void write(int b) throws IOException {
            cachedContent.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            cachedContent.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            cachedContent.write(b, off, len);
        }

        @Override
        public void flush() throws IOException {
            cachedContent.flush();
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // 구현 필요 없음 - 항상 준비됨
        }
    }
}
