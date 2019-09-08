package com.kanjisoup.coffee.stain.processor.edge.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.kanjisoup.coffee.stain.processor.edge.app.config")
public class EdgeTransformerApp {

  public static void main(String[] args) {
    SpringApplication.run(EdgeTransformerApp.class);
  }

}
