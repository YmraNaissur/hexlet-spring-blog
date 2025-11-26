package ru.naissur.daytime;

import jakarta.annotation.PostConstruct;

public class Day implements Daytime {

  @Override
  public String getName() {
    return "day";
  }

  @PostConstruct
  public void init() {
    var message = "\nBean Day is initialized!\n";
    System.out.println(message);
  }
}
