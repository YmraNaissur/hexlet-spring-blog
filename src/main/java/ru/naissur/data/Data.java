package ru.naissur.data;

import ru.naissur.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Data {

  private Data() {
    // private constructor to prevent instantiation
  }

  private static final int ITEMS_COUNT = 4;
  private static int idCounter = ITEMS_COUNT;

  public static List<User> getUsers() {
    List<User> users = new ArrayList<>();

    users.add(new User(getNextId(), "mokaravaev@yandex.ru", "Max", "Karavaev", LocalDate.of(1986, Month.JANUARY, 31), LocalDate.now(), LocalDate.now()));

    List<User> admins = List.of(
        new User(getNextId(), "gjoinsey1@blogger.com", "Glynn", "Joinsey", LocalDate.of(1987, Month.MARCH, 29), LocalDate.now(), LocalDate.now()),
        new User(getNextId(), "scrosi4@cam.ac.uk", "Sarina", "Crosi", LocalDate.of(1990, Month.APRIL, 10), LocalDate.now(), LocalDate.now()),
        new User(getNextId(), "brundle@cam.ac.uk", "Emmit", "Brundle", LocalDate.of(1993, Month.DECEMBER, 2), LocalDate.now(), LocalDate.now())
    );

    users.addAll(admins);
    Collections.shuffle(users);

    return users;
  }

  public static long getNextId() {
    return ++idCounter;
  }
}
