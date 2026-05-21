package vn.io.vutiendat3601.relationaldatabaselocking.exception;

public class ConflictResourceException extends RuntimeException {
  public ConflictResourceException(String message) {
    super(message);
  }
}
