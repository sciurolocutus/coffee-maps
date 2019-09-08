package com.kanjisoup.coffee.stain.submission.app.config;

import com.kanjisoup.coffee.stain.sdk.config.StainQueueClientConfig;
import com.kanjisoup.coffee.stain.sdk.config.StainQueueConnectionFactoryConfig;
import com.kanjisoup.coffee.stain.submission.config.StainSubmissionServiceConfig;
import com.rabbitmq.client.ConnectionFactory;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.kanjisoup.coffee.stain.submission")
@EnableConfigurationProperties({StainQueueClientConfig.class,
    StainQueueConnectionFactoryConfig.class, StainSubmissionServiceConfig.class})
public class SubmissionAppConfig {

  @Bean
  public static ConnectionFactory buildConnectionFactory(StainQueueConnectionFactoryConfig config)
      throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri(config.getUri());

    return factory;
  }
}
