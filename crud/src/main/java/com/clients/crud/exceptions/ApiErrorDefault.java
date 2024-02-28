package com.clients.crud.exceptions;

import java.io.Serializable;
import java.time.Instant;

public record ApiErrorDefault(
    Instant timestamp,
    Integer status,
    String error,
    String message) implements Serializable {
  public static final Long serialVersionUID = 1L;
}