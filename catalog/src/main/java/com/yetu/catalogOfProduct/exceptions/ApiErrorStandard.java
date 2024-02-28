package com.yetu.catalogOfProduct.exceptions;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;

@Data
public class ApiErrorStandard implements Serializable {
  public static final Long serialVersionUID = 1L;

  private Instant timestamp;
  private Integer status;
  private String error;
  private String message;
}
