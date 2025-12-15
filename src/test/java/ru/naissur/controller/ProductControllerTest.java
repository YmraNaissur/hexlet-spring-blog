package ru.naissur.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.naissur.dto.product.ProductUpdateDTO;
import ru.naissur.mapper.ProductMapper;
import ru.naissur.model.Product;
import ru.naissur.repository.ProductRepository;

import java.util.Optional;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ProductMapper productMapper;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private ProductRepository productRepository;

  @Test
  void testUpdate() throws Exception {
    // arrange
    Product existingProduct = new Product();
    existingProduct.setId(1L);
    existingProduct.setTitle("Car");
    existingProduct.setPrice(100_000);
    existingProduct.setVendorCode(12321L);
    when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));

    Product updatedProduct = new Product();
    updatedProduct.setId(1L);
    updatedProduct.setTitle("Notebook");
    updatedProduct.setPrice(70_000);
    updatedProduct.setVendorCode(12321L);
    when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

    ProductUpdateDTO productUpdateDTO = new ProductUpdateDTO();
    productUpdateDTO.setTitle("Notebook");
    productUpdateDTO.setPrice(70_000);

    // act
    var result = mockMvc.perform(put("/api/products/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(productUpdateDTO)))
        .andExpect(status().isOk())
        .andReturn();
    var responseBody = result.getResponse().getContentAsString();

    // assert
    verify(productRepository, times(1)).save(updatedProduct);
    assertThatJson(responseBody).node("title").isEqualTo("Notebook");
    assertThatJson(responseBody).node("vendorCode").isEqualTo("12321");
  }
}