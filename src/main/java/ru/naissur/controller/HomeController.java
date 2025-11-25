package ru.naissur.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.naissur.component.UserProperties;
import ru.naissur.data.Data;
import ru.naissur.model.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HomeController {

  private final UserProperties userProperties;

  @GetMapping("/")
  public String home() {
    return "Welcome to the Hexlet Spring Blog!";
  }

  @GetMapping("/admins")
  public ResponseEntity<List<String>> admins() {
    List<String> adminEmails = userProperties.getAdmins();
    List<User> users = Data.getUsers();

    List<String> adminNames = users.stream()
        .filter(user -> adminEmails.contains(user.getEmail()))
        .map(u -> u.getFirstName() + " " + u.getLastName())
        .sorted()
        .toList();
    return ResponseEntity.ok(adminNames);
  }

  @GetMapping("/about")
  public String about() {
    return """
          This is a simple Spring blog!<br>
          It is being modified through the course on Spring Boot.
        """;
  }

}
