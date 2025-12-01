package ru.naissur.courses.streams.peer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PeerCounter {

  public static void main(String[] args) {
    List<Map<String, String>> users = List.of(
        Map.of("name", "Bronn", "gender", "male", "birthday", "1973-03-23"),
        Map.of("name", "Reigar", "gender", "male", "birthday", "1973-11-03"),
        Map.of("name", "Eiegon", "gender", "male", "birthday", "1963-11-03"),
        Map.of("name", "Sansa", "gender", "female", "birthday", "2012-11-03"),
        Map.of("name", "Jon", "gender", "male", "birthday", "1980-11-03"),
        Map.of("name", "Robb", "gender", "male", "birthday", "1980-05-14"),
        Map.of("name", "Tisha", "gender", "female", "birthday", "2012-11-03"),
        Map.of("name", "Rick", "gender", "male", "birthday", "2012-11-03"),
        Map.of("name", "Joffrey", "gender", "male", "birthday", "1999-11-03"),
        Map.of("name", "Edd", "gender", "male", "birthday", "1973-11-03")
    );

    Map<Integer, Long> result = PeerCounter.getMenCountByYear(users);
    System.out.println(result);
  }

  /**
   * Метод возвращает Map, в котором ключ - это год рождения, а значение – это количество мужчин, родившихся в этот год
   */
  public static Map<Integer, Long> getMenCountByYear(List<Map<String, String>> people) {
    // преобразуем в список, содержащий только мужчин
    return people.stream()
        .filter(p -> p.get("gender").equalsIgnoreCase("male"))
        .collect(Collectors.groupingBy(m -> LocalDate.parse(m.get("birthday")).getYear(), Collectors.counting()));
  }

}
