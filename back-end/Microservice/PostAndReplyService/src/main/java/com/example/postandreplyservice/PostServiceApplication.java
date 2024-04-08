package com.example.postandreplyservice;

import com.example.postandreplyservice.dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableMongoRepositories
@EnableFeignClients
public class PostServiceApplication {

    @Autowired
    PostRepository postRepository;

    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);
    }
}
