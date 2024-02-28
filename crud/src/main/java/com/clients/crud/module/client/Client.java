package com.clients.crud.module.client;

import java.time.Instant;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Client {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;
  String name;
  String bi;
  Double income;
  Integer children;
  @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
  Instant birthDate;

  public Client(String name, String bi, Double income, Instant birthDate, Integer children) {
    this.name = name;
    this.bi = bi;
    this.income = income;
    this.birthDate = birthDate;
    this.children = children;
  }
}
