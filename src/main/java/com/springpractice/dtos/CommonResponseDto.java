package com.springpractice.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.springpractice.enums.CommonResponseStatusEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommonResponseDto<T> {
    private final T data;
    private final String message;
    private final CommonResponseStatusEnum status;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
//    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timeStamp;
    private String path = null;

    public CommonResponseDto(T data, String message, CommonResponseStatusEnum status, LocalDateTime now, String path) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.timeStamp = now;
        this.path = path;
    }

    public static <T> CommonResponseDto<T> success() {
        return new CommonResponseDto<>(null, null, CommonResponseStatusEnum.SUCCESS, LocalDateTime.now(), null);
    }

    public static <T> CommonResponseDto<T> success(T data) {
        return new CommonResponseDto<>(data, null, CommonResponseStatusEnum.SUCCESS, LocalDateTime.now(), null);
    }

    public static <T> CommonResponseDto<T> success(T data, String message) {
        return new CommonResponseDto<>(data, message, CommonResponseStatusEnum.SUCCESS, LocalDateTime.now(), null);
    }

    public static <T> CommonResponseDto<T> fail(String message) {
        return new CommonResponseDto<>(null, message, CommonResponseStatusEnum.FAIL, LocalDateTime.now(), null);
    }

    public void changeRequestPath(String requestPath) {
        this.path = requestPath;
    }

    public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

//    private CommonResponseDto(Builder<T> builder) {
//        this.data = builder.data;
//        this.message = builder.message;
//        this.status = builder.status;
//    }

//    public static class Builder<T> {
//        private T data;
//        private String message;
//        private CommonResponseStatusEnum status;
//
//        public Builder<T> data(T data) {
//            this.data = data;
//            return this;
//        }
//
//        public Builder<T> message(String message) {
//            this.message = message;
//            return this;
//        }
//
//        public Builder<T> status(CommonResponseStatusEnum status) {
//            this.status = status;
//            return this;
//        }
//
//        public CommonResponseDto<T> build() {
//            return new CommonResponseDto<>(this);
//        }
//    }
}


