package vn.io.vutiendat3601.distributedlocking.exception;

public class DuplicatedIdempotentKeyException extends RuntimeException {
  public DuplicatedIdempotentKeyException(String message) {
    super(message);
  }
}
