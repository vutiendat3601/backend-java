package vn.io.vutiendat3601.javaconcurrency.session05performance.imageprocessing;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Color {

  private Color() {}

  public static void recolorMultithreaded(
      BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
    var threads = new ArrayList<Thread>();
    var width = originalImage.getWidth();
    var height = originalImage.getHeight() / numberOfThreads;

    for (var i = 0; i < numberOfThreads; i++) {
      final var threadMultiplier = i;

      var thread =
          new Thread(
              () -> {
                var xOrigin = 0;
                var yOrigin = height * threadMultiplier;
                recolorImage(originalImage, resultImage, xOrigin, yOrigin, width, height);
              });

      threads.add(thread);
    }

    threads.forEach(Thread::start);

    for (var thread : threads) {
      try {
        thread.join();
      } catch (InterruptedException e) {
      }
    }
  }

  public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
    recolorImage(
        originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
  }

  public static void recolorImage(
      BufferedImage originalImage,
      BufferedImage resultImage,
      int leftCorner,
      int topCorner,
      int width,
      int height) {
    for (var x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
      for (var y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
        recolorPixel(originalImage, resultImage, x, y);
      }
    }
  }

  public static void recolorPixel(
      BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
    var rgb = originalImage.getRGB(x, y);

    var red = getRed(rgb);
    var green = getGreen(rgb);
    var blue = getBlue(rgb);

    var newRed = red;
    var newGreen = green;
    var newBlue = blue;

    if (isShadeOfGray(red, green, blue)) {
      newRed = min(255, red + 10);
      newGreen = max(0, green - 80);
      newBlue = max(0, blue - 20);
    }
    var newRGB = createRGBFromColors(newRed, newGreen, newBlue);
    setRGB(resultImage, x, y, newRGB);
  }

  public static void setRGB(BufferedImage image, int x, int y, int rgb) {
    image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
  }

  public static boolean isShadeOfGray(int red, int green, int blue) {
    return abs(red - green) < 30 && abs(red - blue) < 30 && abs(green - blue) < 30;
  }

  public static int createRGBFromColors(int red, int green, int blue) {
    var rgb = 0;

    rgb |= blue;
    rgb |= green << 8;
    rgb |= red << 16;

    rgb |= 0xFF000000;

    return rgb;
  }

  public static int getRed(int rgb) {
    return (rgb & 0x00FF0000) >> 16;
  }

  public static int getGreen(int rgb) {
    return (rgb & 0x0000FF00) >> 8;
  }

  public static int getBlue(int rgb) {
    return rgb & 0x000000FF;
  }
}
