package ru.naissur.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
  private Long id;
  private String title;
  private String body;
}
