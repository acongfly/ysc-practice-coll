package com.acongfly.study.esstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class EsstudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EsstudyApplication.class, args);
    }

}
