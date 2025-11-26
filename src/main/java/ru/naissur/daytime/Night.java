package ru.naissur.daytime;

import jakarta.annotation.PostConstruct;

public class Night implements Daytime {

  @Override
  public String getName() {
    return "night";
  }

  @PostConstruct
  public void init() {
    var message = "\nBean Night is initialized!\n";
    System.out.println(message);
  }
}
