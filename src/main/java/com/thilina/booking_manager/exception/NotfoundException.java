package com.thilina.booking_manager.exception;

public class NotfoundException extends RuntimeException{
    // Parameterless Constructor
    public NotfoundException() {}

    // Constructor that accepts a message
    public NotfoundException(String message)
    {
        super(message);
    }
}
