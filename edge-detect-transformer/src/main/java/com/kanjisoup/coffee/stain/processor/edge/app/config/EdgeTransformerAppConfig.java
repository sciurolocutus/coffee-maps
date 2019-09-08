package com.kanjisoup.coffee.stain.processor.edge.app.config;

import com.kanjisoup.coffee.stain.processor.edge.listener.Listener;
import com.kanjisoup.coffee.stain.sdk.config.StainQueueClientConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;

public class EdgeTransformerAppConfig {

  @Bean
  Queue queue(StainQueueClientConfig config) {
    return new Queue(config.getRoutingKey(), false);
  }

  @Bean
  TopicExchange exchange(StainQueueClientConfig config) {
    return new TopicExchange(config.getExchange());
  }

  @Bean
  Binding binding(Queue queue, TopicExchange exchange) {
    return BindingBuilder.bind(queue).to(exchange).with(queue.getName());
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
