package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.waitnotify.matrixmultiplier;

import java.io.FileWriter;
import java.io.IOException;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MatrixMultiplierConsumer extends Thread {
  private final int N;
  private final String file;
  private final ThreadSafeQueue queue;

  private void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
    for (var r = 0; r < N; r++) {
      var stringJoiner = new StringJoiner(" ");
      for (var c = 0; c < N; c++) {
        stringJoiner.add("%.2f".formatted(matrix[r][c]));
      }
      fileWriter.write(stringJoiner.toString() + "\n");
    }
  }

  @Override
  public void run() {
    while (true) {
      var matrixPair = queue.poll();
      if (matrixPair == null) {
        log.info("No more matrices to read from the queue, consumer is terminating");
        break;
      }

      var ans = multiplyMatrix(matrixPair.first, matrixPair.second);

      try (var fileWriter = new FileWriter(file)) {
        saveMatrixToFile(fileWriter, ans);
        fileWriter.flush();
      } catch (IOException e) {
      }
    }
  }

  private float[][] multiplyMatrix(float[][] m1, float[][] m2) {
    var ans = new float[N][N];
    for (var r = 0; r < N; r++) {
      for (var c = 0; c < N; c++) {
        for (var k = 0; k < N; k++) {
          ans[r][c] += m1[r][k] * m2[k][c];
        }
      }
    }
    try {
      Thread.sleep(1);
    } catch (InterruptedException e) {
    }
    return ans;
  }
}
