package com.kanjisoup.coffee.stain.submission.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="stain.queue")
@Data
public class StainQueueConnectionFactoryConfig {
    private String uri;
}
