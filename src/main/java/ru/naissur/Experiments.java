package ru.naissur;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public class Experiments {
  public static void main(String[] args) {
    User user1 = new User(1, "Ivan", "Ivanov");
    User user2 = new User(2, "Ivan", "Ivanov");

    System.out.println(user1.equals(user2));
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
