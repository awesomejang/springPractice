package com.springpractice.common.advice;

import com.springpractice.dtos.CommonResponseDto;
import com.springpractice.excetpions.InCorrectUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class RestExceptionControllerAdvice {

    private final MessageSource messageSource;
    private final Logger log = LoggerFactory.getLogger(RestExceptionControllerAdvice.class);

    public RestExceptionControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(InCorrectUserException.class)
    public ResponseEntity<CommonResponseDto<Void>> handleInCorrectUserException(InCorrectUserException exception) {
//        log.error("InCorrectUserException: {}", exception.getMessage());
        log.error("InCorrectUserException: ", exception);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(CommonResponseDto.fail(messageSource.getMessage("incorrect.user.info", null, Locale.getDefault())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseDto<Void>> handelException(Exception exception) {
//        log.error("Exception: {}", exception.getMessage());
        log.error("Exception: ", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponseDto.fail(messageSource.getMessage("unknown.error", null, Locale.getDefault())));
    }

}
