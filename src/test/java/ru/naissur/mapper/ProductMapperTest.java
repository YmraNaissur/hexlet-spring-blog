package ru.naissur.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.naissur.dto.product.ProductDTO;
import ru.naissur.dto.product.ProductUpdateDTO;
import ru.naissur.model.Product;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

  private final ProductMapper productMapper = new ProductMapper();

  @Test
  @DisplayName("Product is mapped correctly to ProductDTO")
  void shouldMapAllFieldsCorrectly() {
    // Arrange
    Product product = new Product();
    product.setId(1L);
    product.setTitle("Notebook");
    product.setPrice(99999);
    product.setVendorCode(123456789L);
    product.setCreatedAt(LocalDate.of(2024, 1, 1));
    product.setUpdatedAt(LocalDate.of(2024, 12, 1));

    // Act
    ProductDTO result = productMapper.toDTO(product);

    // Assert
    assertNotNull(result);
    assertEquals(1L, result.getId());
    assertEquals("Notebook", result.getTitle());
    assertEquals(99999, result.getPrice());
    assertEquals(123456789L, result.getVendorCode());
    assertEquals(LocalDate.of(2024, 1, 1), result.getCreatedAt());
    assertEquals(LocalDate.of(2024, 12, 1), result.getUpdatedAt());
  }

  @Test
  @DisplayName("Updates Product fields from ProductUpdateDTO correctly")
  void shouldUpdateProductFieldsCorrectly() {
    // Arrange
    ProductUpdateDTO updateDTO = new ProductUpdateDTO();
    updateDTO.setTitle("Mouse");
    updateDTO.setPrice(1500);

    Product product = new Product();
    product.setId(1L);
    product.setVendorCode(987654321L);
    product.setCreatedAt(LocalDate.now());

    // Act
    productMapper.enrich(updateDTO, product);

    // Assert
    assertEquals("Mouse", product.getTitle());
    assertEquals(1500, product.getPrice());
    assertEquals(987654321L, product.getVendorCode());
    assertNotNull(product.getCreatedAt());
  }
}