package com.springpractice.dtos;


import com.springpractice.enums.CommonResponseStatusEnum;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommonResponseDto<T> {
    private final T data;
    private final String message;
    private final CommonResponseStatusEnum status;
    private final LocalDateTime timeStamp;

    public CommonResponseDto(T data, String message, CommonResponseStatusEnum status, LocalDateTime now) {
        this.data = data;
        this.message = message;
        this.status = status;
        this.timeStamp = now;
    }

    public static <T> CommonResponseDto<T> success() {
        return new CommonResponseDto<>(null, null, CommonResponseStatusEnum.SUCCESS, LocalDateTime.now());
    }

    public static <T> CommonResponseDto<T> success(T data) {
        return new CommonResponseDto<>(data, null, CommonResponseStatusEnum.SUCCESS, LocalDateTime.now());
    }

    public static <T> CommonResponseDto<T> success(T data, String message) {
        return new CommonResponseDto<>(data, message, CommonResponseStatusEnum.SUCCESS, LocalDateTime.now());
    }

    public static <T> CommonResponseDto<T> fail(String message) {
        return new CommonResponseDto<>(null, message, CommonResponseStatusEnum.FAIL, LocalDateTime.now());
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


