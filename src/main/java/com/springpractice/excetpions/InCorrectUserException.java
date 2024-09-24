package com.springpractice.excetpions;

public class InCorrectUserException extends RuntimeException{

    public InCorrectUserException(String message) {
        super(message);
    }

    public InCorrectUserException(String message, Throwable cause) {
        super(message, cause);
    }

}
