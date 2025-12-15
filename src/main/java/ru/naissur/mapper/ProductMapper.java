package ru.naissur.mapper;

import org.springframework.stereotype.Component;
import ru.naissur.dto.product.ProductDTO;
import ru.naissur.dto.product.ProductUpdateDTO;
import ru.naissur.model.Product;

@Component
public class ProductMapper {

  public ProductDTO toDTO(Product product) {
    ProductDTO productDTO = new ProductDTO();
    productDTO.setId(product.getId());
    productDTO.setTitle(product.getTitle());
    productDTO.setPrice(product.getPrice());
    productDTO.setVendorCode(product.getVendorCode());
    productDTO.setCreatedAt(product.getCreatedAt());
    productDTO.setUpdatedAt(product.getUpdatedAt());
    return productDTO;
  }

  public void enrich(ProductUpdateDTO productUpdateDTO, Product product) {
    product.setTitle(productUpdateDTO.getTitle());
    product.setPrice(productUpdateDTO.getPrice());
  }

}
