package vn.io.vutiendat3601.javaconcurrency.session02threadcreation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomizedThread extends Thread {
  @Override
  public void run() {
    log.info("We're now in thread {}.", getName());
  }
}
