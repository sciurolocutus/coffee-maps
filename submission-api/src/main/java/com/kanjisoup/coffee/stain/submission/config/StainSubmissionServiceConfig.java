package com.kanjisoup.coffee.stain.submission.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="stain.submission")
@Data
public class StainSubmissionServiceConfig {
    private String rootDir;
    private String submittedDir;
    private String completedDir;
}
