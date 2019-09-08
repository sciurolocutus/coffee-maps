package com.kanjisoup.coffee.stain.processor.edge.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stain.submission")
@Data
public class StainProcessorConfig {

  private String rootDir;
  private String submittedDir;
  private String completedDir;
}
