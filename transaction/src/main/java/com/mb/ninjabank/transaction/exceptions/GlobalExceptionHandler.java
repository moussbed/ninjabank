package com.mb.ninjabank.transaction.exceptions;

import com.mb.ninjabank.shared.common.exceptions.BadRequestException;
import com.mb.ninjabank.shared.common.exceptions.ForbiddenException;
import com.mb.ninjabank.shared.common.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage())
                .title("Resource not found")
                .build();
        return ResponseEntity.of(Optional.of(errorResponse));
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<ErrorResponse> notFoundException(ForbiddenException exception){
        ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatus.FORBIDDEN, exception.getMessage())
                .title("Forbidden")
                .build();
        return ResponseEntity.of(Optional.of(errorResponse));
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<ErrorResponse> badRequestException(BadRequestException exception){
        ErrorResponse errorResponse = ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, exception.getMessage())
                .title("Bad Request")
                .build();
        return ResponseEntity.of(Optional.of(errorResponse));
    }

}
