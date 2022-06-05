package io.devsecoops.topsecret.application.service.domain;

import io.devsecoops.topsecret.application.domain.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtil {
  public static void assertPositions(Position expected, Position actual, double acceptedDelta) {
    assertEquals(expected.getX(), actual.getX(), acceptedDelta);
    assertEquals(expected.getY(), actual.getY(), acceptedDelta);
  }
}
