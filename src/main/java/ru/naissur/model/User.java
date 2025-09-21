package ru.naissur.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {

  private Long id;
  @NotBlank(message = "Name cannot be empty")
  private String name;
  @NotBlank(message = "Email cannot be empty")
  private String email;

}
