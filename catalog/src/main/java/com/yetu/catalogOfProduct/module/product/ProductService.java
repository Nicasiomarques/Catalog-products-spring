package com.yetu.catalogOfProduct.module.product;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.dao.DataIntegrityViolationException;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.yetu.catalogOfProduct.module.category.CategoryRepository;
import com.yetu.catalogOfProduct.exceptions.EntityNotFoundException;
import com.yetu.catalogOfProduct.exceptions.DatabaseException;

@Service
public class ProductService {
  @Autowired
  private ProductRepository repository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Transactional(readOnly = true)
  public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
    return repository.findAll(pageRequest)
        .map(product -> new ProductDTO(product, product.getCategories()));
  }

  @Transactional(readOnly = true)
  public ProductDTO findById(Long id) {
    return repository.findById(id)
        .map(item -> new ProductDTO(item, item.getCategories()))
        .orElseThrow(() -> new EntityNotFoundException("Product not found"));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public ProductDTO add(ProductDTO productDTO) {
    Product newProduct = new Product();
    copyDtoToEntity(productDTO, newProduct);
    return new ProductDTO(repository.save(newProduct));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public ProductDTO updateById(Long id, ProductDTO productDTO) {
    return repository.findById(id).map(foundProduct -> {
      copyDtoToEntity(productDTO, foundProduct);
      return new ProductDTO(repository.save(foundProduct));
    }).orElseThrow(() -> new EntityNotFoundException("Product not found"));
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public void remove(Long id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Product with id " + id + " not found.");
    }
    try {
      repository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Unable to delete product associated with a product.");
    }
  }

  private <T> void setIfNotNull(T value, Consumer<T> setter) {
    if (value != null) setter.accept(value);
  }

  public void copyDtoToEntity(ProductDTO dto, Product entity) {
    setIfNotNull(dto.name(), entity::setName);
    setIfNotNull(dto.date(), entity::setDate);
    setIfNotNull(dto.price(), entity::setPrice);
    setIfNotNull(dto.imgUrl(), entity::setImgUrl);
    setIfNotNull(dto.description(), entity::setDescription);

    if (dto.categories() != null) {
      entity.getCategories().clear();
      dto.categories().stream()
          .filter(categoryDto -> categoryDto.id() != null)
          .map(categoryDto -> categoryRepository.getReferenceById(categoryDto.id()))
          .forEach(entity.getCategories()::add);
    }
  }
}
