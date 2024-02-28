package com.clients.crud.module.client;

import java.util.List;
import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {
  @Autowired
  ClientService service;

  @PostMapping
  public ResponseEntity<ClientDTO> add(@RequestBody ClientDTO client) {
    ClientDTO addedItem = service.add(client);
    URI itemUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addedItem.id())
        .toUri();
    return ResponseEntity.created(itemUri).body(addedItem);
  }

  @GetMapping
  public ResponseEntity<List<ClientDTO>> getAll() {
    return ResponseEntity.ok(service.getAll());
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(service.getById(id));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> remove(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
 
  @PutMapping(value = "/{id}")
  public ResponseEntity<ClientDTO> updateById(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
    return ResponseEntity.ok(service.updateById(id, clientDTO));
  }
}
