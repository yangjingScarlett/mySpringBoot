package com.yang.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching//如果要使用缓存机制，在spring boot中还是要使用@EnableCaching
public class MySpringbootjpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringbootjpaApplication.class, args);
	}
}
