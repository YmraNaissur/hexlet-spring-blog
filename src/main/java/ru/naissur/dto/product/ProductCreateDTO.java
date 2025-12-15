package ru.naissur.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCreateDTO {

  private String title;
  private int price;
  private long vendorCode;

}
