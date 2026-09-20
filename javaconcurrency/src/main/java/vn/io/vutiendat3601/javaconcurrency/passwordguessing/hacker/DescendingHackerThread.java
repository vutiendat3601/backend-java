package vn.io.vutiendat3601.javaconcurrency.passwordguessing.hacker;

import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.javaconcurrency.passwordguessing.system.Vault;

@Slf4j
public class DescendingHackerThread extends HackerThread {
  private static final int MIN_PASSWORD = Integer.MIN_VALUE;

  public DescendingHackerThread(Vault vault) {
    super(vault);
  }

  @Override
  public void run() {
    int pwd = Integer.MAX_VALUE;
    while (pwd > MIN_PASSWORD) {
      guessPassword(pwd);
      pwd--;
    }
    guessPassword(MIN_PASSWORD);
  }
}
