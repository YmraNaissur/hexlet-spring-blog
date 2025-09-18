package ru.naissur.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "Welcome to the Hexlet Spring Blog!";
  }

  @GetMapping("/about")
  public String about() {
    return """
          This is This is simple Spring blog!<br>
          It is being modified through the course on Spring Boot.
        """;
  }

}
