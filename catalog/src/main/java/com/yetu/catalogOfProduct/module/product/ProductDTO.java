package com.yetu.catalogOfProduct.module.product;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import com.yetu.catalogOfProduct.module.category.CategoryDTO;
import com.yetu.catalogOfProduct.module.category.Category;

import java.util.ArrayList;
import java.util.List;

public record ProductDTO(
    Long id,
    String name,
    String description,
    Double price,
    String imgUrl,
    Instant date,
    List<CategoryDTO> categories) implements Serializable {

  private static final long serialVersionUID = 1L;

  public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
    this(id, name, description, price, imgUrl, date, new ArrayList<>());
  }

  public ProductDTO(Product entity) {
    this(entity.getId(), entity.getName(), entity.getDescription(), entity.getPrice(), entity.getImgUrl(),
        entity.getDate());
  }

  public ProductDTO(Product entity, Set<Category> categories) {
    this(entity);
    categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
  }
}
