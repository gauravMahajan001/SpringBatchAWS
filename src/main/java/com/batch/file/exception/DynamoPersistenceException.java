package com.batch.file.exception;

public class DynamoPersistenceException extends  RuntimeException{
    public DynamoPersistenceException(String message) {
        super(message);
    }

    public DynamoPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
