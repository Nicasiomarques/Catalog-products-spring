package com.yetu.catalogOfProduct.module.category;

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
@RequestMapping(value = "/categories")
public class CategoryController {
  @Autowired
  private CategoryService service;

  @GetMapping
  public ResponseEntity<Page<CategoryDTO>> findAllPaged(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "50") Integer itemsPerPage,
      @RequestParam(defaultValue = "ASC") String direction,
      @RequestParam(defaultValue = "name") String orderBy) {
    PageRequest pageRequest = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Direction.fromString(direction), "name"));
    return ResponseEntity.ok(service.findAllPaged(pageRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remove(@PathVariable Long id) {
    service.remove(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<CategoryDTO> add(@RequestBody CategoryDTO categoryDTO) {
    CategoryDTO addedItem = service.add(categoryDTO);
    URI itemUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addedItem.id())
        .toUri();
    return ResponseEntity.created(itemUri).body(addedItem);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
    return ResponseEntity.ok(service.updateById(id, categoryDTO));
  }
}