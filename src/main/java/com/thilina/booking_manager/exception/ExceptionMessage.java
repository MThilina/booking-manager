package com.thilina.booking_manager.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessage {
    NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND.value(),
            "Related details not found"),

    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR",HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Something went wrong and cause an INTERNAL_SERVER_ERROR"),

    FORBIDDEN("FORBIDDEN",HttpStatus.FORBIDDEN.value(),
            "Don't have access to execute this operation"),

    UNAUTHORIZED("UNAUTHORIZED",HttpStatus.UNAUTHORIZED.value(),"User UnAuthenticated"),

    BAD_REQUEST("BAD_REQUEST",HttpStatus.BAD_REQUEST.value(),
            "Bad request, Request cannot process");

    private final String errorMessage;
    private final int errorCode;
    private final String developerErrCode;

     ExceptionMessage(String errorMessage,int errorCode,String developerErrCode){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.developerErrCode = developerErrCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDeveloperErrCode() {return developerErrCode;}
}
