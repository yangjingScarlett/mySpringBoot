package com.yang.springboot;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching//如果要使用缓存机制，在spring boot中还是要使用@EnableCaching
public class MySpringbootjpaApplication implements CommandLineRunner {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public Queue wiselyQueue() {
        return new Queue("my-queue");
    }

    @Override
    public void run(String... args) throws Exception {
        rabbitTemplate.convertAndSend("my-queue", "来自RabbitMQ的问候！");
    }

    public static void main(String[] args) {
        SpringApplication.run(MySpringbootjpaApplication.class, args);
    }
}
