package ru.naissur.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.naissur.daytime.Daytime;

@RestController
@RequiredArgsConstructor
public class WelcomeController {

  private final Daytime daytime;

  @GetMapping(path = "/welcome")
  public String welcome() {
    var message = "It is " + daytime.getName() + " now!";
    return message + " Welcome to Spring!";
  }
}
