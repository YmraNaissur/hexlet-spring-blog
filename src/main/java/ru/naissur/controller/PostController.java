package ru.naissur.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.naissur.model.Post;

@RestController
@Validated
@RequestMapping("/posts")
public class PostController {

  List<Post> posts = new ArrayList<>();

  @GetMapping()
  public ResponseEntity<List<Post>> getPosts() {
    if (CollectionUtils.isEmpty(posts)) {
      return ResponseEntity
          .notFound()
          .build();
    } else {
      return ResponseEntity
          .ok()
          .header("X-Content-Length", String.valueOf(posts.size()))
          .body(posts);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable String id) {
    var post = findById(id);
    if (post.isEmpty()) {
      return ResponseEntity
          .notFound()
          .build();
    } else {
      return ResponseEntity.of(findById(id));
    }
  }

  @PostMapping()
  public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
    posts.add(post);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(post);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Post> updatePost(@PathVariable String id, @Valid @RequestBody Post data) {
    var maybePost = findById(id);
    if (maybePost.isEmpty()) {
      return ResponseEntity
          .notFound()
          .build();
    } else {
      var post = maybePost.get();
      post.setTitle(data.getTitle());
      post.setAuthor(data.getAuthor());
      post.setCreatedAt(data.getCreatedAt());
      post.setContent(data.getContent());
      return ResponseEntity
          .ok()
          .body(data);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePost(@PathVariable String id) {
    var post = findById(id);
    if (post.isEmpty()) {
      return ResponseEntity
          .notFound()
          .build();
    } else {
      posts.removeIf(p -> p.getTitle().equals(id));
      return ResponseEntity
          .noContent()
          .build();
    }
  }

  private Optional<Post> findById(String id) {
    return posts.stream()
        .filter(p -> p.getTitle().equals(id))
        .findFirst();
  }
}
