package vn.io.vutiendat3601.chatsaas.exception;

public class AppNotFoundException extends RuntimeException {
  public AppNotFoundException(String message) {
    super(message);
  }
}
