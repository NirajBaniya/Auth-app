package com.substring.auth.auth_app_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
     ErrorResponse internalServerError = new ErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(internalServerError);
    }
}
