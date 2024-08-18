package com.springpractice.dtos;


import com.springpractice.enums.CommonResponseStatusEnum;
import lombok.Getter;

@Getter
public class CommonResponseDto<T> {
    private final T data;
    private final String message;
    private final CommonResponseStatusEnum status;

    public CommonResponseDto(T data, String message, CommonResponseStatusEnum status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static <T> CommonResponseDto<T> success(T data) {
        return new CommonResponseDto<>(data, null, CommonResponseStatusEnum.SUCCESS);
    }

    public static <T> CommonResponseDto<T> success(T data, String message) {
        return new CommonResponseDto<>(data, message, CommonResponseStatusEnum.SUCCESS);
    }

    public static <T> CommonResponseDto<T> fail(String message) {
        return new CommonResponseDto<>(null, message, CommonResponseStatusEnum.FAIL);
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


