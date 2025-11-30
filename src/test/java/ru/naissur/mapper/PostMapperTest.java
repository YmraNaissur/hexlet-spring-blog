package ru.naissur.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.naissur.model.Post;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostMapperTest {

  private PostMapper postMapper;

  @BeforeEach
  void setUp() {
    postMapper = new PostMapper();
  }

  @Test
  public void testMapToDTO() {
    // arrange
    Post post = new Post();
    post.setId(1L);
    post.setBody("post body");
    post.setTitle("post title");

    // act
    var postDto = postMapper.toDTO(post);

    // assert
    assertEquals(1L, postDto.getId());
    assertEquals("post body", postDto.getBody());
    assertEquals("post title", postDto.getTitle());
  }

}