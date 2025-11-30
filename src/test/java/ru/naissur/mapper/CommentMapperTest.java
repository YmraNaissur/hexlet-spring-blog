package ru.naissur.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.naissur.model.Comment;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentMapperTest {

  private CommentMapper commentMapper;

  @BeforeEach
  void setUp() {
    commentMapper = new CommentMapper();
  }

  @Test
  void testMapToDTO() {
    // arrange
    Comment comment = new Comment();
    comment.setId(1L);
    comment.setBody("comment body");
    comment.setPostId(1L);
    comment.setCreatedAt(LocalDate.now());

    // act
    var commentDTO = commentMapper.toDTO(comment);

    // assert
    assertEquals(1L, commentDTO.getId());
    assertEquals("comment body", commentDTO.getBody());
  }
}