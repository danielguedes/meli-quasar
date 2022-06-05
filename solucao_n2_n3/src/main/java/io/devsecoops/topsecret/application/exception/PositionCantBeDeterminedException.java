package io.devsecoops.topsecret.application.exception;

public class PositionCantBeDeterminedException extends RuntimeException {
  private static final long serialVersionUID = -5109805098109849374L;

  private static final String errorCode = "ERR0001";

  public PositionCantBeDeterminedException() {
  }

  public PositionCantBeDeterminedException(String s) {
    super(s);
  }

  public PositionCantBeDeterminedException(String message, Throwable cause) {
    super(message, cause);
  }

  public PositionCantBeDeterminedException(Throwable cause) {
    super(cause);
  }


}
