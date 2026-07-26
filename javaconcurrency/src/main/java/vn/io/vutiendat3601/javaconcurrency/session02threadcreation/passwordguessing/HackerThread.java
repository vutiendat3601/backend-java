package vn.io.vutiendat3601.javaconcurrency.session02threadcreation.passwordguessing;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public abstract class HackerThread extends Thread {
  protected Vault vault;

  {
    setPriority(Thread.MAX_PRIORITY);
  }

  @Override
  public void start() {
    log.info("Starting thread {}.", getName());
    super.start();
  }
}
