package com.example.kalah.exception;


public class ValidationException extends RuntimeException {


    public ValidationException() {
    }

    /**
     * Instantiates a new Validation exception.
     *
     * @param message error message to be displayed
     */
    public ValidationException(String message) {
        super(message);
    }
}
