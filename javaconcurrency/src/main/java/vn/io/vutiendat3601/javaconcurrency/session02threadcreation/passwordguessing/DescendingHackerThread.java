package vn.io.vutiendat3601.javaconcurrency.session02threadcreation.passwordguessing;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DescendingHackerThread extends HackerThread {
  private static final int MIN_PASSWORD = Integer.MIN_VALUE;

  public DescendingHackerThread(Vault vault) {
    super(vault);
  }

  @Override
  public void run() {
    for (int guess = Integer.MAX_VALUE; guess >= MIN_PASSWORD; guess--) {
      if (vault.isCorrectPassword(guess)) {
        log.info("{} guessed the password {}", getName(), guess);
        System.exit(0);
      }
    }
  }
}
