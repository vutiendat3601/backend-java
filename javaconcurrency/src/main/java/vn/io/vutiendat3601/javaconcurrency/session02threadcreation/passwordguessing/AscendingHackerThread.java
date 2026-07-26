package vn.io.vutiendat3601.javaconcurrency.session02threadcreation.passwordguessing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AscendingHackerThread extends HackerThread {
  private static final int MAX_PASSWORD = Integer.MAX_VALUE;

  public AscendingHackerThread(Vault vault) {
    super(vault);
  }

  @Override
  public void run() {
    for (int guess = 0; guess <= MAX_PASSWORD; guess++) {
      if (vault.isCorrectPassword(guess)) {
        log.info("{} guessed the password {}", getName(), guess);
        System.exit(0);
      }
    }
  }
}
