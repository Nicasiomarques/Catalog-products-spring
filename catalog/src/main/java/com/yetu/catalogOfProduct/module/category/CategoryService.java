package com.yetu.catalogOfProduct.module.category;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.yetu.catalogOfProduct.exceptions.EntityNotFoundException;
import com.yetu.catalogOfProduct.exceptions.DatabaseException;

@Service
public class CategoryService {
  @Autowired
  private CategoryRepository repository;

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
    return repository.findAll(pageRequest).map(CategoryDTO::new);
  }

  @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
  public CategoryDTO findById(Long id) {
    return repository.findById(id).map(CategoryDTO::new)
        .orElseThrow(() -> new EntityNotFoundException("Category not found"));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public CategoryDTO add(CategoryDTO categoryDTO) {
    Category item = new Category(categoryDTO.name());
    return new CategoryDTO(repository.save(item));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public CategoryDTO updateById(Long id, CategoryDTO categoryDTO) {
    return repository.findById(id).map(foundItem -> {
      foundItem.setName(categoryDTO.name());
      return new CategoryDTO(repository.save(foundItem));
    }).orElseThrow(() -> new EntityNotFoundException("Category not found"));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void remove(Long id) {
    if (!repository.existsById(id)) {
      throw new DatabaseException("Category with id " + id + " not found.");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Unable to delete category associated with a product.");
    }
  }
}
