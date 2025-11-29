package ru.naissur;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Experiments {
  public static void main(String[] args) {
    User user1 = new User(1, "Ivan", "Ivanov");
    User user2 = new User(2, "Ivan", "Ivanov");

    log.info(String.valueOf(user1.equals(user2)));
  }
}

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = {"firstName", "lastName"})
class User {
  private long id;
  private String firstName;
  private String lastName;
}
