package com.kanjisoup.coffee.stain.sdk.transform;

import java.awt.Image;
import java.awt.image.BufferedImage;

public interface ImageTransformer {

  public BufferedImage transform(Image image);
}
