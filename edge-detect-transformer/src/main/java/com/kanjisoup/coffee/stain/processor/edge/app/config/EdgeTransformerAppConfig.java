package com.kanjisoup.coffee.stain.processor.edge.app.config;

import com.kanjisoup.coffee.stain.processor.edge.config.StainProcessorConfig;
import com.kanjisoup.coffee.stain.processor.edge.listener.Listener;
import com.kanjisoup.coffee.stain.sdk.config.StainQueueClientConfig;
import com.kanjisoup.coffee.stain.sdk.config.StainQueueConnectionFactoryConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.kanjisoup.coffee.stain.processor.edge")
@EnableConfigurationProperties({StainQueueClientConfig.class, StainProcessorConfig.class})
public class EdgeTransformerAppConfig {

  @Bean
  Queue queue(StainQueueClientConfig config) {
    return new Queue(config.getRoutingKey(), false);
  }

  @Bean
  FanoutExchange exchange(StainQueueClientConfig config) {
    return new FanoutExchange(config.getExchange(), false, false);
  }

  @Bean
  Binding binding(Queue queue, FanoutExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange);
  }

  @Bean
  SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
      MessageListenerAdapter listenerAdapter, StainQueueClientConfig config) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(config.getRoutingKey());
    container.setMessageListener(listenerAdapter);

    return container;
  }

  @Bean
  MessageListenerAdapter listenerAdapter(Listener listener) {
    return new MessageListenerAdapter(listener, "receiveMessage");
  }
}
