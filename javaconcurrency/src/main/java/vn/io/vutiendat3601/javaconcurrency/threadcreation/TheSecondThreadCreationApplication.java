package vn.io.vutiendat3601.javaconcurrency.threadcreation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TheSecondThreadCreationApplication {
  public static void main(String[] args) {
    var thread = new CustomizedThread();
    thread.start();
  }
}
