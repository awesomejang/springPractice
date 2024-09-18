package com.springpractice.common.advice;

import com.springpractice.dtos.CommonResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@RestControllerAdvice
public class CommonResponseAdvice implements ResponseBodyAdvice<Object> {

    private final HttpServletRequest request;

    public CommonResponseAdvice(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (ResponseEntity.class.isAssignableFrom(returnType.getParameterType())) {
            Type genericParameterType = returnType.getGenericParameterType();

            if (genericParameterType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericParameterType;
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];

                if (actualTypeArgument instanceof ParameterizedType) {
                    ParameterizedType actualParameterizedType = (ParameterizedType) actualTypeArgument;
                    return CommonResponseDto.class.isAssignableFrom((Class<?>) actualParameterizedType.getRawType());
                }
            }
        }
        return false;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(body instanceof CommonResponseDto) {
            CommonResponseDto<?> commo = (CommonResponseDto<?>) body;
            String requestURI = this.request.getRequestURI();
            commo.changeRequestPath(requestURI);
        }
        return body;
    }
}
