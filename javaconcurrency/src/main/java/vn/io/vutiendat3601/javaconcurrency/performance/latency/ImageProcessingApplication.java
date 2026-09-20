package vn.io.vutiendat3601.javaconcurrency.performance.latency;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageProcessingApplication {
  private static final String SOURCE_FILE = "many-flowers.jpg";
  private static final String MULTITHREADED_DESTINATION_FILE =
      "multithreaded-flowers-recolored.jpg";
  private static final String SINGLE_THREAD_DESTINATION_FILE = "singlethread-flowers-recolored.jpg";

  public static void main(String[] args) throws IOException {
    var imgInpStream = ClassLoader.getSystemResourceAsStream(SOURCE_FILE);
    var originalImage = ImageIO.read(imgInpStream);

    /* #: Multithreaded */
    var resultImage =
        new BufferedImage(
            originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    var numberOfThreads = 3;
    // var numberOfThreads = Runtime.getRuntime().availableProcessors();
    var startTime = Instant.now();
    Color.recolorMultithreaded(originalImage, resultImage, numberOfThreads);
    var endTime = Instant.now();
    var duration = Duration.between(startTime, endTime).toMillis();
    log.info("Multithreaded ({} threads) took {}ms", numberOfThreads, duration);
    ImageIO.write(resultImage, "jpg", new File(MULTITHREADED_DESTINATION_FILE));
    /* # Multithreaded */

    /* #: Single thread */
    resultImage =
        new BufferedImage(
            originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
    startTime = Instant.now();
    Color.recolorSingleThreaded(originalImage, resultImage);
    endTime = Instant.now();
    duration = Duration.between(startTime, endTime).toMillis();
    log.info("Single thread took {}ms", duration);
    ImageIO.write(resultImage, "jpg", new File(SINGLE_THREAD_DESTINATION_FILE));
    /* # Single thread */
  }
}
