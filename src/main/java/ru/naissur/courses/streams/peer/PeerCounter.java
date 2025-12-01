package ru.naissur.courses.streams.peer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PeerCounter {
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
