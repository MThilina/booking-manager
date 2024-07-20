package com.thilina.booking_manager.exception;

import com.thilina.booking_manager.dto.ErrorResponseDto;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationServiceException.class)
    protected ResponseEntity<ErrorResponseDto> handleBadRequestException(ApplicationServiceException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage(),ex.getErrorCode(),ex.getDeveloperErrMessage());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getStatus()));
    }
//    @ExceptionHandler(NotfoundException.class)
//    protected ResponseEntity<ErrorResponseDTO> handleNotFoundException(RuntimeException ex) {
//        ErrorResponseDTO errorResponse = new ErrorResponseDTO(ex.getMessage(),HttpStatus.NOT_FOUND.value(), Instant.now());
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
