package ru.naissur.model;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Post {

  private int userId;
  @NotBlank(message = "Title cannot be blank")
  private String title;
  @NotBlank(message = "Content cannot be blank")
  private String content;
  private String author;
  private LocalDateTime createdAt;

}
