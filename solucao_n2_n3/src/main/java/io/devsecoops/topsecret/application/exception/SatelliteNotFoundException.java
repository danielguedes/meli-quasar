package io.devsecoops.topsecret.application.exception;

public class SatelliteNotFoundException extends RuntimeException {
  private static final long serialVersionUID = -5100109849493820983L;

  private static final String errorCode = "ERR0002";

  public SatelliteNotFoundException() {
  }

  public SatelliteNotFoundException(String s) {
    super(s);
  }

  public SatelliteNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public SatelliteNotFoundException(Throwable cause) {
    super(cause);
  }


}
