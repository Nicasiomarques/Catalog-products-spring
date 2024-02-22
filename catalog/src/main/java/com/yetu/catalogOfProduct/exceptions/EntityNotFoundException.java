package com.yetu.catalogOfProduct.exceptions;

public class EntityNotFoundException extends RuntimeException  {
  public static final Long serialVersionUID = 1L;
  public EntityNotFoundException(String msg) {
    super(msg);
  }
}
