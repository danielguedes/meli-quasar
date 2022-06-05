package io.devsecoops.topsecret.application.service.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocationDecipherTest {

  static final float[][] POSITIONS = new float[][]{{-500, -200}, {100f, -100f}, {500f, 100f}};
  static final float ACCEPTED_DELTA = 0.0001f;

  LocationDecipher decipher;

  private static void assertPositions(float[] expected, float[] actual, float acceptedDelta) {
    assertEquals(expected[0], actual[0], acceptedDelta);
    assertEquals(expected[1], actual[1], acceptedDelta);
  }

  @BeforeEach
  void prepare() {
    decipher = new LocationDecipher(POSITIONS);
  }

  @Test
  void givenPositionInSamePositionOfASatellite_whenGettingLocation_shouldSatellitePosition() {
    float[] expected = new float[]{-500f, -200f};
    float[] distances = getDistances(POSITIONS, expected);
    float[] actual = decipher.getLocation(distances);
    printResult(distances, expected, actual);
    assertPositions(expected, actual, ACCEPTED_DELTA);
  }

  @Test
  void givenLocationInUpperRightQuadrant_whenGettingLocation_shouldReturnCorrectLocation() {
    float[] expected = new float[]{250f, 250f};
    float[] distances = getDistances(POSITIONS, expected);
    float[] actual = decipher.getLocation(distances);
    printResult(distances, expected, actual);
    assertPositions(expected, actual, ACCEPTED_DELTA);
  }

  @Test
  void givenLocationInBottomRightQuadrant_whenGettingLocation_shouldReturnCorrectLocation() {
    float[] expected = new float[]{200f, -120f};
    float[] distances = getDistances(POSITIONS, expected);
    float[] actual = decipher.getLocation(distances);
    printResult(distances, expected, actual);
    assertPositions(expected, actual, ACCEPTED_DELTA);
  }

  @Test
  void givenLocationInBottomLeftQuadrant_whenGettingLocation_shouldReturnCorrectLocation() {
    float[] expected = new float[]{-100f, -120f};
    float[] distances = getDistances(POSITIONS, expected);
    float[] actual = decipher.getLocation(distances);
    printResult(distances, expected, actual);
    assertPositions(expected, actual, ACCEPTED_DELTA);
  }

  @Test
  void givenLocationInUpperLeftQuadrant_whenGettingLocation_shouldReturnCorrectLocation() {
    float[] expected = new float[]{-100f, 120f};
    float[] distances = getDistances(POSITIONS, expected);
    float[] actual = decipher.getLocation(distances);
    printResult(distances, expected, actual);
    assertPositions(expected, actual, ACCEPTED_DELTA);
  }

  @Test
  void givenOnlyTwoDistances_whenGettingLocation_shouldThrowIllegalArgumentException() {
    float[] expected = new float[]{150f, 150f};
    float[] distances = getDistances(new float[][]{{-500, -200}, {100f, -100f}}, expected);
    assertThrows(IllegalArgumentException.class, () -> decipher.getLocation(distances));
    printResult(distances, expected, null);
  }

  @Test
  void givenFourDistances_whenGettingLocation_shouldThrowIllegalArgumentException() {
    float[] expected = new float[]{150f, 150f};
    float[] distances = getDistances(new float[][]{{-500, -200}, {100f, -100f}, {-80f, 600f}, {110f, 40f}}, expected);
    assertThrows(IllegalArgumentException.class, () -> decipher.getLocation(distances));
    printResult(distances, expected, null);
  }

  private float[] getDistances(float[][] positions, float[] position) {
    float[] result = new float[positions.length];
    for (int i = 0; i < positions.length; i++) {
      result[i] = calculateDistance(positions[i], position);
    }
    return result;
  }

  private float calculateDistance(float[] pos1, float[] pos2) {
    double xOperand = Math.pow((pos2[0] - pos1[0]), 2);
    double yOperand = Math.pow((pos2[1] - pos1[1]), 2);
    return (float) Math.sqrt(xOperand + yOperand);
  }

  private void printResult(float[] distances, float[] expected, float[] actual) {
    System.out.println("---------------------------------------------------------------------");
    System.out.printf("Distances: %s%n", Arrays.toString(distances));
    System.out.printf(" Expected: {x=%1$s, y=%2$s}%n", expected[0], expected[1]);
    if (actual != null) {
      System.out.printf("   Actual: {x=%1$s, y=%2$s}%n", actual[0], actual[1]);
    }
  }
}