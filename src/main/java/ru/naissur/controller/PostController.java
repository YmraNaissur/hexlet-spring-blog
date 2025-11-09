package ru.naissur.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.model.Post;
import ru.naissur.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  @Autowired
  private PostRepository postRepository;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Post getPostById(@PathVariable Long id) {
    Optional<Post> post = postRepository.findById(id);
    return post.orElseThrow(() -> new EntityNotFoundException("Post with id = " + id + " not found"));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@RequestBody Post post) {
    postRepository.save(post);
    return post;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePost(@PathVariable Long id) {
    postRepository.deleteById(id);
  }

}
