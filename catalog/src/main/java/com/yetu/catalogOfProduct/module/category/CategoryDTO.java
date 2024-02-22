package com.yetu.catalogOfProduct.module.category;

import java.io.Serializable;

public record CategoryDTO(Long id, String name) implements Serializable {
  public static final Long serialVersionUID = 1L;

  public CategoryDTO(Category category) {
    this(category.getId(), category.getName());
  }
}