package ru.naissur.controller;

import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.model.Post;
import ru.naissur.repository.PostRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostRepository postRepository;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Post getPostById(@PathVariable Long id) {
    Optional<Post> post = postRepository.findById(id);
    return post.orElseThrow(() -> new ResourceNotFoundException("Post with id = " + id + " not found"));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<Post> getAllPosts(
      @RequestParam(name = "page", defaultValue = "1") Integer page,
      @RequestParam(name = "size", defaultValue = "5") Integer size) {

    Sort sort = Sort.by("createdAt").descending();
    Pageable pageable = PageRequest.of(page - 1, size, sort);

    return postRepository.findByPublishedTrue(pageable);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@Valid @RequestBody Post post) {
    postRepository.save(post);
    return post;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePost(@PathVariable Long id) {
    postRepository.deleteById(id);
  }

}
