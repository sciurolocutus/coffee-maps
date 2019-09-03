package com.kanjisoup.coffee.stain.submission.app;

import com.kanjisoup.coffee.stain.submission.config.StainQueueClientConfig;
import com.kanjisoup.coffee.stain.submission.config.StainQueueConnectionFactoryConfig;
import com.kanjisoup.coffee.stain.submission.config.StainSubmissionServiceConfig;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.kanjisoup.coffee.stain.submission")
@EnableConfigurationProperties({StainQueueClientConfig.class, StainQueueConnectionFactoryConfig.class, StainSubmissionServiceConfig.class})
public class SubmissionApp {

    public static void main(String[] args) {
        SpringApplication.run(SubmissionApp.class);
    }

    @Bean
    public static ConnectionFactory buildConnectionFactory(StainQueueConnectionFactoryConfig config) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri(config.getUri());

        return factory;
    }
}
