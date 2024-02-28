package com.clients.crud.exceptions;

public class NotFoundException extends RuntimeException {
  public static final Long serialVersionUID = 1L;

  public NotFoundException(String message) {
    super(message);
  }
}
