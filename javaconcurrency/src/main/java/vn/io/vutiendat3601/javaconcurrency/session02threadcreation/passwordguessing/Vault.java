package vn.io.vutiendat3601.javaconcurrency.session02threadcreation.passwordguessing;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vault {
  private int password;

  public boolean isCorrectPassword(int guess) {
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
    }
    return password == guess;
  }
}
