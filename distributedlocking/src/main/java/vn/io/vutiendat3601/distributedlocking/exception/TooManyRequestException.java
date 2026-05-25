package vn.io.vutiendat3601.distributedlocking.exception;

public class TooManyRequestException extends RuntimeException {
  public TooManyRequestException(String message) {
    super(message);
  }
}
