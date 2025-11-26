package ru.naissur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.naissur.daytime.Daytime;

@RestController
public class WelcomeController {

  @Autowired
  private Daytime daytime;

  @GetMapping(path = "/welcome")
  public String welcome() {
    var message = "It is " + daytime.getName() + " now!";
    return message + " Welcome to Spring!";
  }
}
