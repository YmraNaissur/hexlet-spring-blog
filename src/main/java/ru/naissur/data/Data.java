package ru.naissur.data;

import ru.naissur.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.LongStream;

public class Data {
  private static final int ITEMS_COUNT = 4;

  private static int idCounter = ITEMS_COUNT;

  public static List<User> getUsers() {
    List<Long> ids = LongStream
        .range(1, ITEMS_COUNT + 1)
        .boxed()
        .toList();

    List<User> users = new ArrayList<>();

    users.add(new User(getNextId(), "Max Karavaev", "mokaravaev@yandex.ru"));

    List<User> admins = List.of(
        new User(getNextId(), "Glynn Joinsey", "gjoinsey1@blogger.com"),
        new User(getNextId(), "Sarina Crosi", "scrosi4@cam.ac.uk"),
        new User(getNextId(), "Emmit Brundle", "brundle@cam.ac.uk")
    );

    users.addAll(admins);
    Collections.shuffle(users);

    return users;
  }

  public static long getNextId() {
    long id = ++idCounter;
    return id;
  }
}
