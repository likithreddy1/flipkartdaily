package com.flipkartdaily.exception;

public class DuplicateItemException extends RuntimeException {
    public DuplicateItemException(String message){
        super(message);
    }
}
