package ru.naissur.controller;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

  @GetMapping("/")
  public List<Post> getPosts() {
    return posts;
  }

  @GetMapping("/{id}")
  public Optional<Post> getPostById(@PathVariable String id) {
    return findById(id);
  }

  @PostMapping("/")
  public Post createPost(@Valid @RequestBody Post post) {
    posts.add(post);
    return post;
  }

  @PutMapping("/{id}")
  public Post updatePost(@PathVariable String id, @Valid @RequestBody Post data) {
    var maybePost = findById(id);
    if (maybePost.isPresent()) {
      var post = maybePost.get();
      post.setTitle(data.getTitle());
      post.setAuthor(data.getAuthor());
      post.setCreatedAt(data.getCreatedAt());
      post.setContent(data.getContent());
    }
    return data;
  }

  @DeleteMapping("/{id}")
  public void deletePost(@PathVariable String id) {
    posts.removeIf(p -> p.getTitle().equals(id));
  }

  private Optional<Post> findById(String id) {
    return posts.stream()
        .filter(p -> p.getTitle().equals(id))
        .findFirst();
  }
}
