package ru.naissur.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import ru.naissur.exception.ResourceAlreadyExistsException;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.model.Product;
import ru.naissur.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

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
