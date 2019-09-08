package com.kanjisoup.coffee.stain.processor.edge.transformer.util;

import java.awt.image.BufferedImage;

public class Grayscale {

  public static void greyscaleInPlace(BufferedImage image) {
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = image.getRGB(x, y);
        int a = (pixel >> 24) & 0xff;
        int r = (pixel >> 16) & 0xff;
        int g = (pixel >> 8) & 0xff;
        int b = pixel & 0xff;

        int avg = (r + g + b) / 3;
        pixel = (a << 24 | avg << 16 | avg << 8 | avg);

        image.setRGB(x, y, pixel);
      }
    }
  }
}