package ru.naissur.controller.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.model.User;
import ru.naissur.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

  private final UserRepository userRepository;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public User getUser(@PathVariable Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() -> new ResourceNotFoundException("User with id = " + id + " not found"));
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User createUser(@Valid @RequestBody User user) {
    userRepository.save(user);
    return user;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
