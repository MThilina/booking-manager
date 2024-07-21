package com.thilina.booking_manager.exception;


import com.thilina.booking_manager.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(),HttpStatus.BAD_REQUEST.value(), Instant.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotfoundException.class)
    protected ResponseEntity<ErrorResponseDto> handleNotFoundException(NotfoundException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(),HttpStatus.NOT_FOUND.value(), Instant.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotReturnedException.class)
    protected ResponseEntity<ErrorResponseDto> handleBookNotReturnedException(BookNotReturnedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(),HttpStatus.NOT_FOUND.value(), Instant.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataCurruptedException.class)
    protected ResponseEntity<ErrorResponseDto> handleDataCorruptException(DataCurruptedException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(),HttpStatus.NOT_FOUND.value(), Instant.now());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}