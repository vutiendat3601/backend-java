package vn.io.vutiendat3601.javaconcurrency.session05performance.imageprocessing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageProcessingApplication {
  private static final String SOURCE_FILE = "many-flowers.jpg";
  private static final String DESTINATION_FILE = "many-flowers-recolored.jpg";

  public static void main(String[] args) throws IOException {
    var imgInpStream = ClassLoader.getSystemResourceAsStream(SOURCE_FILE);
    var originalImage = ImageIO.read(imgInpStream);
    var resultImage =
        new BufferedImage(
            originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

    var startTime = System.currentTimeMillis();
    // Color.recolorSingleThreaded(originalImage, resultImage);
    var numberOfThreads = 3;
    Color.recolorMultithreaded(originalImage, resultImage, numberOfThreads);
    var endTime = System.currentTimeMillis();

    var duration = endTime - startTime;

    var outputFile = new File(DESTINATION_FILE);
    ImageIO.write(resultImage, "jpg", outputFile);

    log.info("duration = {}", duration);
  }
}
