package com.thilina.booking_manager.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException() {}

    // Constructor that accepts a message
    public BadRequestException(String message)
    {
        super(message);
    }
}
