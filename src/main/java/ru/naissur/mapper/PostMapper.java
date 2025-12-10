package ru.naissur.mapper;

import org.springframework.stereotype.Component;
import ru.naissur.dto.PostCreateDTO;
import ru.naissur.dto.PostDTO;
import ru.naissur.model.Post;

@Component
public class PostMapper {

  public PostDTO toDTO(Post post) {
    PostDTO postDTO = new PostDTO();
    postDTO.setId(post.getId());
    postDTO.setTitle(post.getTitle());
    postDTO.setBody(post.getBody());
    return postDTO;
  }

  public Post toEntity(PostCreateDTO postCreateDTO) {
    Post post = new Post();
    post.setTitle(postCreateDTO.getTitle());
    post.setBody(postCreateDTO.getBody());
    return post;
  }

}
