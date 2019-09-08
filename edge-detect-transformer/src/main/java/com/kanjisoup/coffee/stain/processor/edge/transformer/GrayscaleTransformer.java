package com.kanjisoup.coffee.stain.processor.edge.transformer;

import com.kanjisoup.coffee.stain.processor.edge.transformer.util.Grayscale;
import com.kanjisoup.coffee.stain.sdk.transform.ImageTransformer;
import java.awt.image.BufferedImage;
import org.springframework.stereotype.Component;

@Component
public class GrayscaleTransformer implements ImageTransformer {

  @Override
  public BufferedImage transform(BufferedImage image) {
    Grayscale.greyscaleInPlace(image);
    return image;
  }
}
