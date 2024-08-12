package com.springpractice.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream cachedContent;
    private ServletOutputStream outputStream;
    private PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        cachedContent = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (this.writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }
        if (this.outputStream == null) {
            this.outputStream = new CachedBodyServletOutputStream(cachedContent);
        }
        return this.outputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (this.outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }
        if (this.writer == null) {
            this.writer = new PrintWriter(cachedContent, true, StandardCharsets.UTF_8);
        }
        return this.writer;
    }

    public byte[] getCachedContent() {
        return cachedContent.toByteArray();
    }

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
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // No implementation needed
        }
    }
}
