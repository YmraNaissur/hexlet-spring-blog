package ru.naissur.dto.post;

import lombok.Getter;
import lombok.Setter;
import ru.naissur.dto.CommentDTO;

import java.util.List;

@Getter
@Setter
public class PostDTO {

  private Long id;
  private String title;
  private String body;
  private List<CommentDTO> comments;

}
