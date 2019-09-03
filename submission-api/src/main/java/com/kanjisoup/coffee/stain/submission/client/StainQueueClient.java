package com.kanjisoup.coffee.stain.submission.client;

import com.google.gson.Gson;
import com.kanjisoup.coffee.stain.submission.config.StainQueueClientConfig;
import com.kanjisoup.coffee.stain.submission.domain.StainQueueDto;
import com.kanjisoup.coffee.stain.submission.exception.SubmissionFailedException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class StainQueueClient {
    private ConnectionFactory connectionFactory;
    private Gson gson;
    private StainQueueClientConfig config;

    @Autowired
    public StainQueueClient(ConnectionFactory connectionFactory, StainQueueClientConfig config) {
        this.connectionFactory = connectionFactory;
        this.config = config;
        this.gson = new Gson();
    }

    public void submitStain(String filePath, String uuid) throws SubmissionFailedException {
        StainQueueDto dto = new StainQueueDto(filePath, uuid);

        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel();) {
            channel.exchangeDeclare(config.getExchange(), "fanout", false, false, null);
            channel.queueDeclare(config.getRoutingKey(), false, false, false, null);
            channel.queueBind(config.getRoutingKey(), config.getExchange(), config.getRoutingKey());
            channel.basicPublish(config.getExchange(), config.getRoutingKey(), null, gson.toJson(dto).getBytes());
        } catch (TimeoutException e) {
            log.error("Timeout while submitting queue message", e);
            throw new SubmissionFailedException("Timed out submitting request");
        } catch (IOException e) {
            log.error("IOException while submitting queue message", e);
            throw new SubmissionFailedException("IOException: " + e.getMessage());
        }
    }
}
