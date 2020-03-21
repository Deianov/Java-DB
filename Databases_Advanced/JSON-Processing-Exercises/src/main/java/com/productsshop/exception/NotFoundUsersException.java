package com.productsshop.exception;

public class NotFoundUsersException extends RuntimeException{

    public NotFoundUsersException() {
        super();
    }

    public NotFoundUsersException(String message) {
        super(message);
    }

    public NotFoundUsersException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUsersException(Throwable cause) {
        super(cause);
    }
}
