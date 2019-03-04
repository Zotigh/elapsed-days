package edu.cnm.deepdive.util;

import static org.junit.jupiter.api.Assertions.*;

class DateOnlyTest {

  void elapsedDaysNormalized(int year, int month, int day, int expected) {
    assertEquals(expected, DateOnly.elapsedDays(year, month, day));
  }


}