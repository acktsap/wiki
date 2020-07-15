package io.lim.selenium.basic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageUtils {

  /**
   * Calculate difference ratio based on {@code baseImage}.
   *
   * @param baseImage a base image to compare
   * @param targetImage a target image to compare
   * @return a difference ratio (eg. 1.1%, 65.56%)
   */
  public static double calculateDifferenceRatio(final BufferedImage baseImage,
      final BufferedImage targetImage) {
    int diffPixelCount = 0;
    for (int y = 0; y < baseImage.getHeight(); ++y) {
      for (int x = 0; x < baseImage.getWidth(); ++x) {
        // no pixel in corresponding x, y
        if (targetImage.getWidth() <= x || targetImage.getHeight() <= y) {
          ++diffPixelCount;
          continue;
        }

        // on difference pixel
        if (baseImage.getRGB(x, y) != targetImage.getRGB(x, y)) {
          ++diffPixelCount;
          continue;
        }
      }
    }

    final double basePixelCount = baseImage.getWidth() * baseImage.getHeight();
    return 100.0d * ((double) diffPixelCount) / basePixelCount;
  }

  /**
   * Compare two image and generate difference image based on {@code baseImage}.
   *
   * @param baseImage a base image to compare
   * @param targetImage a target image to compare
   * @return difference image
   */
  public static BufferedImage generateDifference(final BufferedImage baseImage,
      final BufferedImage targetImage) {
    final int height = baseImage.getHeight();
    final int width = baseImage.getWidth();
    final BufferedImage differenceImage =
        new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (targetImage.getWidth() <= x || targetImage.getHeight() <= y) {
          differenceImage.setRGB(x, y, 0x80ff0000);
          continue;
        }

        final int basePixel = baseImage.getRGB(x, y);
        final int targetPixel = targetImage.getRGB(x, y);
        if (basePixel == targetPixel) {
          differenceImage.setRGB(x, y, basePixel);
        } else {
          int a = 0xff | baseImage.getRGB(x, y) >> 24,
              r = 0xff & baseImage.getRGB(x, y) >> 16,
              g = 0x00 & baseImage.getRGB(x, y) >> 8,
              b = 0x00 & baseImage.getRGB(x, y);
          int modifiedRGB = a << 24 | r << 16 | g << 8 | b;
          differenceImage.setRGB(x, y, modifiedRGB);
        }
      }
    }

    return differenceImage;
  }

  /**
   * Write image to a file.
   *
   * @param bufferedImage a buffered image to write
   * @param fileName a filename to write
   * @param format a file fornat (eg. png, jpg)
   *
   * @throws IOException on writing failure
   */
  public static void writeToFile(final BufferedImage bufferedImage, final String fileName,
      final String format) throws IOException {
    ImageIO.write(bufferedImage, format, new File(fileName + "." + format));
  }

}
