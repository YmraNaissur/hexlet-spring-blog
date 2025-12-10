package ru.naissur.dto.contact;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactCreateDTO {

  @NotBlank
  @Size(max = 25)
  private String name;

  @NotBlank
  @Size(max = 25)
  private String surname;

  @NotBlank
  @Size(min = 5, max = 15)
  private String phoneNumber;

}
