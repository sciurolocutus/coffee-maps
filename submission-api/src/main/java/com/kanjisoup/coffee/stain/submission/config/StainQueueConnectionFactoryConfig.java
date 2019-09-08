package com.kanjisoup.coffee.stain.submission.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="stain.queue")
@Data
public class StainQueueConnectionFactoryConfig {
    private String uri;
}
