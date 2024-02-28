package com.clients.crud.module.client;

import java.time.Instant;

public record ClientDTO(
    Long id,
    String name,
    String bi,
    Double income,
    Instant birthDate,
    Integer children) {

  public ClientDTO(Client client) {
    this(client.id, client.getName(), client.getBi(), client.getIncome(), client.getBirthDate(), client.getChildren());
  }
}