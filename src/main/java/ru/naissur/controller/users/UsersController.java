package ru.naissur.controller.users;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.model.User;
import ru.naissur.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public User getUser(@PathVariable Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " not found"));
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
