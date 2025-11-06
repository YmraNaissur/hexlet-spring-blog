package ru.naissur.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {

  private Long id;
  @NotBlank(message = "Name cannot be empty")
  private String name;
  @NotBlank(message = "Email cannot be empty")
  private String email;

}
