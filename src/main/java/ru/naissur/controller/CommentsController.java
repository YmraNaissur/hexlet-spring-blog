package ru.naissur.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.model.Comment;
import ru.naissur.repository.CommentRepository;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {

  private final CommentRepository commentRepository;

  @GetMapping(path = "")
  public List<Comment> index() {
    return commentRepository.findAll();
  }

  @PostMapping(path = "")
  @ResponseStatus(HttpStatus.CREATED)
  public Comment create(@RequestBody Comment comment) {
    return commentRepository.save(comment);
  }

  @GetMapping(path = "/{id}")
  public Comment show(@PathVariable Long id) {
    return commentRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));
  }

  @PutMapping(path = "/{id}")
  public Comment update(@PathVariable Long id, @RequestBody Comment comment) {
    var existingComment = commentRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " not found"));

    existingComment.setBody(comment.getBody());
    existingComment.setPostId(comment.getPostId());

    return commentRepository.save(existingComment);
  }

  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) {
    commentRepository.deleteById(id);
  }

}
