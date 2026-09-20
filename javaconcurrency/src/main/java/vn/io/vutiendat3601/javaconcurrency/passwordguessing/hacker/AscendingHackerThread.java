package vn.io.vutiendat3601.javaconcurrency.passwordguessing.hacker;

import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.javaconcurrency.passwordguessing.system.Vault;

@Slf4j
public class AscendingHackerThread extends HackerThread {
  private static final int MAX_PASSWORD = Integer.MAX_VALUE;

  public AscendingHackerThread(Vault vault) {
    super(vault);
  }

  @Override
  public void run() {
    int pwd = Integer.MIN_VALUE;
    while (pwd < MAX_PASSWORD) {
      guessPassword(pwd);
      pwd++;
    }
    guessPassword(MAX_PASSWORD);
  }
}
