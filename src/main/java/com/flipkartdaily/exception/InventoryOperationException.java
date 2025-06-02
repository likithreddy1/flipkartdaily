package com.flipkartdaily.exception;

public class InventoryOperationException extends RuntimeException {
    public InventoryOperationException(String message){
        super(message);
    }
}
