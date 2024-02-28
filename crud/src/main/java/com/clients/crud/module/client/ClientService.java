package com.clients.crud.module.client;

import java.util.function.Consumer;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.clients.crud.exceptions.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
  @Autowired
  ClientRepository repository;

  @Transactional(readOnly = true)
  public ClientDTO getById(Long id) {
    return repository.findById(id).map(ClientDTO::new)
        .orElseThrow(() -> new NotFoundException("Not found client!"));
  }

  @Transactional(readOnly = true)
  public List<ClientDTO> getAll() {
    return repository.findAll().stream().map(ClientDTO::new).toList();
  }

  @Transactional
  public ClientDTO add(ClientDTO dto) {
    Client client = new Client(dto.name(), dto.bi(), dto.income(), dto.birthDate(), dto.children());
    return new ClientDTO(repository.save(client));
  }

  @Transactional
  public ClientDTO updateById(Long id, ClientDTO dto) {
    return repository.findById(id).map(entity -> {
      setIfNotNull(dto.name(), entity::setName);
      setIfNotNull(dto.income(), entity::setIncome);
      setIfNotNull(dto.birthDate(), entity::setBirthDate);
      setIfNotNull(dto.children(), entity::setChildren);
      setIfNotNull(dto.bi(), entity::setBi);
      return new ClientDTO(entity);
    }).orElseThrow(() -> new NotFoundException("Not found client!"));
  }

  @Transactional
  public void delete(Long id) {
    if (!repository.existsById(id)) {
      throw new NotFoundException("Not found client!");
    }
    repository.deleteById(id);
  }

  private <T> void setIfNotNull(T value, Consumer<T> setter) {
    if (value != null)
      setter.accept(value);
  }
}
