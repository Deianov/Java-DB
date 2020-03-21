package com.productsshop.exception;

public class NotFoundCategoriesException extends RuntimeException {

    public NotFoundCategoriesException() {
        super();
    }

    public NotFoundCategoriesException(String message) {
        super(message);
    }

    public NotFoundCategoriesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundCategoriesException(Throwable cause) {
        super(cause);
    }
}
