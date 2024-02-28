package com.clients.crud.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {
  private ApiErrorDefault createError(HttpStatus status, String errorType, String message) {
    return new ApiErrorDefault(Instant.now(), status.value(), errorType, message);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiErrorDefault> notFound(NotFoundException e) {
    HttpStatus statusCode = HttpStatus.NOT_FOUND;
    ApiErrorDefault error = createError(statusCode, "Entity not found", e.getMessage());
    return ResponseEntity.status(statusCode).body(error);
  }
}
