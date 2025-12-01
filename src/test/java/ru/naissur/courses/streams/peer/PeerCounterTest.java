package ru.naissur.courses.streams.peer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PeerCounterTest {

  @Test
  void testGetMenCountByYear() {
    // arrange
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
    Map<Integer, Long> expectedResult = Map.of(1980, 2L, 1999, 1L, 1963, 1L, 1973, 3L, 2012, 1L);

    // act
    var actualResult = PeerCounter.getMenCountByYear(users);

    // assert
    assertEquals(expectedResult, actualResult);
  }
}