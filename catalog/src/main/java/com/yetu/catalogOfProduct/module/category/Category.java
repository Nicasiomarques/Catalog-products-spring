package com.yetu.catalogOfProduct.module.category;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.yetu.catalogOfProduct.module.product.Product;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Category implements Serializable {
  public static final Long serialVersionUID = 1L;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Id Long id;
  private String name;
  
  @ManyToMany(mappedBy = "categories")
  private Set<Product> products = new HashSet<>();

  public Category(String name) {
    this.name = name;
  }
}
