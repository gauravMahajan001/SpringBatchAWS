package com.batch.file.exception;

public class MainframeWriteException extends RuntimeException{
    public MainframeWriteException(String message) {
        super(message);
    }

    public MainframeWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
