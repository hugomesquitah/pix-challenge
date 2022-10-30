package com.project.pixchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class PixChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PixChallengeApplication.class, args);
    }

}
