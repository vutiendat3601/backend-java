package vn.io.vutiendat3601.distributedlocking.exception;

public class NotEnoughBalanceException extends RuntimeException {
  public NotEnoughBalanceException(String message) {
    super(message);
  }
}
