package com.thilina.booking_manager.exception;

import lombok.Getter;

public class ApplicationServiceException extends RuntimeException{
    @Getter private final ExceptionMessage exceptionMessage;
    @Getter private final int errorCode;
    @Getter private String developerErrMessage;


    public ApplicationServiceException(final int errorCode, final String message,
                                       final ExceptionMessage exceptionMessage){
        super(message);
        this.errorCode = errorCode;
        this.exceptionMessage = exceptionMessage;
        this.developerErrMessage = exceptionMessage.getErrorMessage();
    }

}
