package com.yetu.catalogOfProduct.module.product;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
  @Autowired
  private ProductService service;

  @GetMapping
  public ResponseEntity<Page<ProductDTO>> findAllPaged(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "50") Integer itemsPerPage,
      @RequestParam(defaultValue = "ASC") String direction,
      @RequestParam(defaultValue = "name") String orderBy) {
    PageRequest pageRequest = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Direction.fromString(direction), "name"));
    return ResponseEntity.ok(service.findAllPaged(pageRequest));
  }

  @PostMapping
  public ResponseEntity<ProductDTO> add(@RequestBody ProductDTO productDTO) {
    ProductDTO addedItem = service.add(productDTO);
    System.out.println(addedItem);
    URI itemUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addedItem.id())
        .toUri();
    return ResponseEntity.created(itemUri).body(addedItem);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> remove(@PathVariable Long id) {
    service.remove(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
    return ResponseEntity.ok(service.updateById(id, productDTO));
  }
}