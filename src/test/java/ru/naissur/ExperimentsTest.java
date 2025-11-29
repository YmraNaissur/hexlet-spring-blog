package ru.naissur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExperimentsTest {

  @Test
  void testMain() {
    assertDoesNotThrow(() -> Experiments.main(new String[]{}));
  }
}