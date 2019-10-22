package com.house.taskstracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class TasksRulesApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksRulesApplication.class, args);
    }

}
