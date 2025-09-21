package ru.naissur.controller.users;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.naissur.model.Post;

@RestController
@RequestMapping("/api/users")
public class PostsController {

  private final List<Post> posts = new ArrayList<>();

  @GetMapping("/{id}/posts")
  @ResponseStatus(HttpStatus.OK)
  public List<Post> getPostsByUserId(@PathVariable Integer id) {
    return posts.stream()
        .filter(p -> p.getUserId() == id)
        .toList();
  }

  @PostMapping("/{id}/posts")
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@PathVariable Integer id, @RequestBody Post post) {
    post.setUserId(id);
    posts.add(post);
    return post;
  }

}
