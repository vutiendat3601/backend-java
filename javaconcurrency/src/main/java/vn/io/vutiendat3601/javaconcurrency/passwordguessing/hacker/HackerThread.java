package vn.io.vutiendat3601.javaconcurrency.passwordguessing.hacker;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import vn.io.vutiendat3601.javaconcurrency.passwordguessing.system.Vault;

@Getter
@Slf4j
public abstract class HackerThread extends Thread {
  protected Vault vault;

  protected HackerThread(Vault vault) {
    this.vault = vault;
    setPriority(Thread.MAX_PRIORITY);
  }

  @Override
  public void start() {
    log.info("[{}] Starting thread {}.", getName(), getName());
    super.start();
  }

  protected void guessPassword(int pwd) {
    if (vault.isCorrectPassword(pwd)) {
      log.info("Hacked password sucessfully.");
      log.info("{} guessed the password {}", getName(), pwd);
      System.exit(0);
    }
  }
}
