package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.waitnotify.matrixmultiplier;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringJoiner;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MatrixGenerator {
  private final int N;
  private final String file;
  private static final int NUMBER_OF_MATRIX_PAIRS = 100_000;

  public void generate() throws IOException {
    try (var fileWriter = new FileWriter(file)) {
      createMatrices(fileWriter);
      fileWriter.flush();
    }
  }

  private float[] createRow(Random random) {
    var row = new float[N];
    for (var i = 0; i < N; i++) {
      row[i] = random.nextFloat() * random.nextInt(100);
    }
    return row;
  }

  private float[][] createMatrix(Random random) {
    var matrix = new float[N][N];
    for (var i = 0; i < N; i++) {
      matrix[i] = createRow(random);
    }
    return matrix;
  }

  private void saveMatrixToFile(FileWriter fileWriter, float[][] matrix) throws IOException {
    for (var r = 0; r < N; r++) {
      var stringJoiner = new StringJoiner(" ");
      for (var c = 0; c < N; c++) {
        stringJoiner.add("%.2f".formatted(matrix[r][c]));
      }
      fileWriter.write(stringJoiner.toString() + "\n");
    }
  }

  private void createMatrices(FileWriter fileWriter) throws IOException {
    var random = new Random();
    for (var i = 0; i < NUMBER_OF_MATRIX_PAIRS * 2 - 1; i++) {
      var matrix = createMatrix(random);
      saveMatrixToFile(fileWriter, matrix);
      fileWriter.write('\n');
    }
    var matrix = createMatrix(random);
    saveMatrixToFile(fileWriter, matrix);
  }
}
