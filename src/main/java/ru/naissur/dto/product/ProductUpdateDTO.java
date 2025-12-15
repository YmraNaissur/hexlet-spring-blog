package ru.naissur.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpdateDTO {

  @NotBlank
  @Size(max = 100)
  private String title;

  @NotBlank
  @Min(0)
  private int price;

}
