package com.yetu.catalogOfProduct.module.product;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.yetu.catalogOfProduct.module.category.Category;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@NoArgsConstructor
public class Product implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @Column(columnDefinition = "TEXT")
  private String description;
  private String imgUrl;
  private Double price;

  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  private Instant date;

  @ManyToMany
  @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories = new HashSet<>();

  public Product(String name, String description, Double price, String imgUrl, Instant date) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.imgUrl = imgUrl;
    this.date = date;
  }
}