package ru.naissur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.exception.ResourceAlreadyExistsException;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.model.Product;
import ru.naissur.repository.ProductRepository;

import java.util.List;

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
