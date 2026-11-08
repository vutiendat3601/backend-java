package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.waitnotify.matrixmultiplier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MatrixReaderProducer extends Thread {
  private final int N;
  private final String file;
  private final ThreadSafeQueue queue;

  @Override
  public void run() {
    try (var scanner = new Scanner(new File(file))) {
      while (true) {
        var first = readMatrix(scanner);
        var second = readMatrix(scanner);
        if (first == null || second == null) {
          queue.terminate();
          log.info("No more matrices to read. Producer Thread is terminating");
          return;
        }

        var matrixPair = new MatrixPair();
        matrixPair.first = first;
        matrixPair.second = second;

        queue.offer(matrixPair);
      }
    } catch (FileNotFoundException e) {
    }
  }

  private float[][] readMatrix(Scanner scanner) {
    var ans = (float[][]) null;
    if (scanner.hasNext()) {
      ans = new float[N][N];
      for (int r = 0; r < N; r++) {
        for (int c = 0; c < N; c++) {
          ans[r][c] = scanner.nextFloat();
        }
      }
      scanner.nextLine();
    }
    return ans;
  }
}
