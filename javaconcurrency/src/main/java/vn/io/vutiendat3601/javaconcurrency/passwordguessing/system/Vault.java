package vn.io.vutiendat3601.javaconcurrency.passwordguessing.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

@Data
@AllArgsConstructor
public class Vault {
  private int password;

  @SneakyThrows
  public boolean isCorrectPassword(int pwd) {
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
    }
    return password == pwd;
  }
}
