package com.yetu.catalogOfProduct.exceptions;

public class DatabaseException extends RuntimeException  {
  public static final Long serialVersionUID = 1L;
  public DatabaseException(String msg) {
    super(msg);
  }
}
