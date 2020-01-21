package com.kanjisoup.coffee.stain.processor.edge.listener;

import com.google.gson.Gson;
import com.kanjisoup.coffee.stain.processor.edge.config.StainProcessorConfig;
import com.kanjisoup.coffee.stain.processor.edge.listener.exception.UnprocessableException;
import com.kanjisoup.coffee.stain.sdk.domain.StainQueueDto;
import com.kanjisoup.coffee.stain.sdk.transform.ImageTransformer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Listener {

  private ImageTransformer transformer;

  private StainProcessorConfig config;

  public Listener(@Qualifier("edgeDetectTransformer") ImageTransformer transformer,
      StainProcessorConfig config) {
    this.transformer = transformer;
    this.config = config;
  }

  public void receiveMessage(byte[] msg) {
    String message = new String(msg);
    Gson gson = new Gson();
    StainQueueDto stainQueueDto = gson.fromJson(message, StainQueueDto.class);
    log.debug("Got new image on queue: uuid: {}, filePath: {}", stainQueueDto.getUuid(),
        stainQueueDto.getFilePath());

    BufferedImage img = null;
    try {
      img = ImageIO.read(new File(stainQueueDto.getFilePath()));
    } catch (IOException e) {
      log.error("Error reading file {} uuid {}", stainQueueDto.getFilePath(),
          stainQueueDto.getUuid(), e);
      //TODO: in the future, add an "unprocessable" or "error" queue, and submit these there.
      // That does open the whole can of worms of reprocessing, retries, etc, so it'll take a moment.
      throw new UnprocessableException(stainQueueDto.getFilePath() + " could not be read.", e);
    }

    BufferedImage transformed = transformer.transform(img);

    File targetFile = Path.of(config.getCompletedDir(),
        Path.of(stainQueueDto.getFilePath()).getFileName().toString() + ".png")
        .toFile();
    try {
      ImageIO.write(transformed, "PNG", targetFile);
    } catch (IOException e) {
      log.error("Error writing file {} uuid {}", targetFile.getAbsolutePath(),
          stainQueueDto.getUuid(), e);
      throw new UnprocessableException(targetFile.getAbsolutePath() + " could not be saved.", e);
    }
  }
}