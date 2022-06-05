package io.devsecoops.topsecret.decipher;

import java.util.Objects;

public class Position {
  Float x;
  Float y;

  public Position(Float x, Float y) {
    this.x = x;
    this.y = y;
  }

  public Float getX() {
    return x;
  }

  public void setX(Float x) {
    this.x = x;
  }

  public Float getY() {
    return y;
  }

  public void setY(Float y) {
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Position position = (Position) o;
    return Objects.equals(x, position.x) && Objects.equals(y, position.y);
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public String toString() {
    return "Position{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
