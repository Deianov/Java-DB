package com.springdata.exercises.exception;

public class IllegalRepositoryIndexException extends RuntimeException {
    public IllegalRepositoryIndexException() {
    }

    public IllegalRepositoryIndexException(String message) {
        super(message);
    }

    public IllegalRepositoryIndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalRepositoryIndexException(Throwable cause) {
        super(cause);
    }
}
