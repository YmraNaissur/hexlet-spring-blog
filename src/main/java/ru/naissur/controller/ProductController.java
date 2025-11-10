package ru.naissur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.model.Product;
import ru.naissur.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @GetMapping(path = "/{id}")
  public Product findProductById(@PathVariable Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
  }

  @PutMapping(path = "/{id}")
  public Product updateProduct(@PathVariable Long id, @RequestBody Product source) {
    Product target = productRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

    target.setTitle(source.getTitle());
    target.setPrice(source.getPrice());
    productRepository.save(target);
    return target;
  }

  @PostMapping
  public Product createProduct(@RequestBody Product product) {
    return productRepository.save(product);
  }

}
