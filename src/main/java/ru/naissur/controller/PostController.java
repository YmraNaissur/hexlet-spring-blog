package ru.naissur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.dto.PostDTO;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.mapper.PostMapper;
import ru.naissur.model.Post;
import ru.naissur.repository.CommentRepository;
import ru.naissur.repository.PostRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PostMapper postMapper;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO getPostById(@PathVariable Long id) {
    var post = postRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " not found"));
    return postMapper.toDTO(post);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PostDTO> getAllPosts() {
    return postRepository.findAll().stream()
        .map(postMapper::toDTO)
        .toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@Valid @RequestBody Post post) {
    return postRepository.save(post);
  }

  @PutMapping("/{id}")
  public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
    var existingPost = postRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " not found"));

    existingPost.setTitle(post.getTitle());
    existingPost.setBody(post.getBody());

    return postRepository.save(existingPost);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePost(@PathVariable Long id) {
    commentRepository.deleteByPostId(id);
    postRepository.deleteById(id);
  }

}
