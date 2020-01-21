package com.kanjisoup.coffee.stain.processor.edge.transformer;

import com.kanjisoup.coffee.stain.sdk.transform.ImageTransformer;
import edgedetector.detectors.LaplacianEdgeDetector;
import edgedetector.util.Grayscale;
import edgedetector.util.Threshold;
import java.awt.image.BufferedImage;
import org.springframework.stereotype.Component;

@Component
public class EdgeDetectTransformer implements ImageTransformer {

  @Override
  public BufferedImage transform(BufferedImage image) {

    // run Laplacian edge detector
    LaplacianEdgeDetector led = new LaplacianEdgeDetector(Grayscale.imgToGrayPixels(image));

    // get edges
    boolean[][] edges = led.getEdges();

    // make images out of edges
    BufferedImage laplaceImage = Threshold.applyThresholdReversed(edges);

    return laplaceImage;
  }

}
