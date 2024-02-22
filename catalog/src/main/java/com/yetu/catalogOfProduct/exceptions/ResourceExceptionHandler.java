package com.yetu.catalogOfProduct.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {
  private ApiErrorStandard createError(HttpStatus status, String errorType, String message) {
    ApiErrorStandard error = new ApiErrorStandard();
    error.setTimestamp(Instant.now());
    error.setStatus(status.value());
    error.setError(errorType);
    error.setMessage(message);
    return error;
  }
  
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ApiErrorStandard> entityNotFound(EntityNotFoundException e) {
    HttpStatus statusCode = HttpStatus.NOT_FOUND;
    ApiErrorStandard error = createError(statusCode, "Entity not found", e.getMessage());
    return ResponseEntity.status(statusCode).body(error);
  }
 
  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<ApiErrorStandard> database(DatabaseException e) {
    HttpStatus statusCode = HttpStatus.NOT_FOUND;
    ApiErrorStandard error = createError(statusCode, "Database exception", e.getMessage());
    return ResponseEntity.status(statusCode).body(error);
  }
}