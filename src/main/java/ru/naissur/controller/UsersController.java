package ru.naissur.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.naissur.dto.UserDTO;
import ru.naissur.exception.ResourceNotFoundException;
import ru.naissur.mapper.UserMapper;
import ru.naissur.model.User;
import ru.naissur.repository.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UsersController {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO getUser(@PathVariable Long id) {
    User user = userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User with id = " + id + " not found"));

    return userMapper.toDTO(user);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<UserDTO> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(userMapper::toDTO)
        .toList();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User createUser(@Valid @RequestBody User user) {
    userRepository.save(user);
    return user;
  }

  @PutMapping("/{id}")
  public User updateUser(@PathVariable Long id, @RequestBody User user) {
    var existingUser = userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User with id = " + id + " not found"));

    existingUser.setFirstName(user.getFirstName());
    existingUser.setLastName(user.getLastName());
    existingUser.setBirthDate(user.getBirthDate());
    existingUser.setEmail(user.getEmail());

    return userRepository.save(existingUser);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Long id) {
    userRepository.deleteById(id);
  }
}
