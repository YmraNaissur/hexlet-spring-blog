package ru.naissur.mapper;

import org.springframework.stereotype.Component;
import ru.naissur.dto.CommentDTO;
import ru.naissur.model.Comment;

@Component
public class CommentMapper {

  public CommentDTO toDTO(Comment comment) {
    CommentDTO commentDTO = new CommentDTO();
    commentDTO.setId(comment.getId());
    commentDTO.setBody(comment.getBody());
    return commentDTO;
  }

}
