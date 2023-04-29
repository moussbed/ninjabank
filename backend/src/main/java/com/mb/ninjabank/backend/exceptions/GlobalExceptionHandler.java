package com.mb.ninjabank.backend.exceptions;

import com.mb.ninjabank.shared.common.exceptions.NotFoundException;
import com.mb.ninjabank.shared.common.exceptions.WithdrawalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage())
                .title("Resource not found")
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {WithdrawalException.class})
    public ResponseEntity<ErrorResponse> withdrawalException(WithdrawalException exception){
        ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatus.FORBIDDEN, exception.getMessage())
                .title("Forbidden")
                .build();
        return new ResponseEntity<>(errorResponse,HttpStatus.FORBIDDEN);
    }

}
