package ru.naissur.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.naissur.dto.product.ProductDTO;
import ru.naissur.dto.product.ProductUpdateDTO;
import ru.naissur.exception.ResourceAlreadyExistsException;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.mapper.ProductMapper;
import ru.naissur.model.Product;
import ru.naissur.repository.ProductRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

  private final ProductRepository productRepository;

  private final ProductMapper productMapper;

  @GetMapping(path = "")
  public List<Product> findFilteredByPrice(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
    Sort sort = Sort.by("price").ascending();

    if (min == null) {
      if (max == null) {
        return productRepository.findAll(sort);
      } else {
        min = 0;
      }
    } else if (max == null) {
      max = Integer.MAX_VALUE;
    }

    return productRepository.findByPriceBetween(min, max, sort);
  }

  @GetMapping(path = "/{id}")
  public Product findProductById(@PathVariable Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
  }

  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDTO source) {
    Product existingProduct = productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

    productMapper.enrich(source, existingProduct);
    var savedProduct = productRepository.save(existingProduct);
    return productMapper.toDTO(savedProduct);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@RequestBody Product product) {
    List<Product> existingProduct = productRepository.findAll();
    if (existingProduct.contains(product)) {
      throw new ResourceAlreadyExistsException(
          "Product with title " + product.getTitle() + " and price " + product.getPrice() + " already exists");
    } else {
      return productRepository.save(product);
    }
  }

}
