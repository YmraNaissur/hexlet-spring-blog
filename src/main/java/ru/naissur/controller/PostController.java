package ru.naissur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.dto.post.PostCreateDTO;
import ru.naissur.dto.post.PostDTO;
import ru.naissur.dto.post.PostUpdateDTO;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.mapper.CommentMapper;
import ru.naissur.mapper.PostMapper;
import ru.naissur.model.Post;
import ru.naissur.repository.CommentRepository;
import ru.naissur.repository.PostRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PostMapper postMapper;
  private final CommentMapper commentMapper;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO getPostById(@PathVariable Long id) {
    var post = postRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " not found"));

    var comments = commentRepository.findByPostId(id);
    var commentDTOs = comments.stream()
        .map(commentMapper::toDTO)
        .toList();
    var postDTO = postMapper.toDTO(post);
    postDTO.setComments(commentDTOs);

    return postDTO;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<PostDTO> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    List<PostDTO> postDTOs = new ArrayList<>(posts.size());
    for (Post post : posts) {
      var commentDTOs = commentRepository
          .findByPostId(post.getId())
          .stream()
          .map(commentMapper::toDTO)
          .toList();
      var postDTO = postMapper.toDTO(post);
      postDTO.setComments(commentDTOs);
      postDTOs.add(postDTO);
    }

    return postDTOs;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PostDTO createPost(@Valid @RequestBody PostCreateDTO postCreateDTO) {
    Post post = postMapper.toEntity(postCreateDTO);
    Post savedPost = postRepository.save(post);
    return postMapper.toDTO(savedPost);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public PostDTO updatePost(@RequestBody @Valid PostUpdateDTO postData, @PathVariable Long id) {
    var existingPost = postRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " not found"));

    postMapper.enrichPost(postData, existingPost);
    var savedPost = postRepository.save(existingPost);
    return postMapper.toDTO(savedPost);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePost(@PathVariable Long id) {
    commentRepository.deleteByPostId(id);
    postRepository.deleteById(id);
  }

}
