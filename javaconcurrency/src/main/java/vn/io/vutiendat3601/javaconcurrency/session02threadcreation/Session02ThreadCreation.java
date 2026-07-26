package vn.io.vutiendat3601.javaconcurrency.session02threadcreation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Session02ThreadCreation {
  public static void main(String[] args) throws InterruptedException {
    var thread = new CustomizedThread();
    thread.start();
  }
}
