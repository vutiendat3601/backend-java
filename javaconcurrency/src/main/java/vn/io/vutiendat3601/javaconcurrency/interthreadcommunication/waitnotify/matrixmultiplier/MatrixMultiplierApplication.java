package vn.io.vutiendat3601.javaconcurrency.interthreadcommunication.waitnotify.matrixmultiplier;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatrixMultiplierApplication {
  private static final int N = 10;
  private static final String FILE_INPUT = "matrices.txt";
  private static final String FILE_OUTPUT = "matrix_result.txt";

  public static void main(String[] args) throws IOException {
    var matrixGenerator = new MatrixGenerator(N, FILE_INPUT);
    matrixGenerator.generate();

    var threadSafeQueue = new ThreadSafeQueue();
    var matrixReader = new MatrixReaderProducer(N, FILE_INPUT, threadSafeQueue);
    var matrixConsumer = new MatrixMultiplierConsumer(N, FILE_OUTPUT, threadSafeQueue);

    matrixConsumer.start();
    matrixReader.start();
  }
}
