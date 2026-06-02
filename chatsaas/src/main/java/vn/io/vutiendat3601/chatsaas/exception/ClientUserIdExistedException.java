package vn.io.vutiendat3601.chatsaas.exception;

public class ClientUserIdExistedException extends RuntimeException {
  public ClientUserIdExistedException(String message) {
    super(message);
  }
}
