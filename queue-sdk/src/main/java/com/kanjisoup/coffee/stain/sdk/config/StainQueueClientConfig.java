package com.kanjisoup.coffee.stain.sdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "stain.queue")
@Data
public class StainQueueClientConfig {

  private String exchange;
  private String routingKey;
}