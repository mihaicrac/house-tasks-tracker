package com.house.tasktracker;

import com.house.tasktracker.infrastructure.OrderOffsetChangedSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({ OrderOffsetChangedSink.class })
public class TasksExecutionsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TasksExecutionsApplication.class, args);
    }

}
