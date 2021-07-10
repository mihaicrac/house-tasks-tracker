package com.house.tasktracker.controller;

import com.house.tasktracker.domain.HouseTaskRepository;
import com.house.tasktracker.infrastructure.NotificationsClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class TasksController {

    private NotificationsClient notificationsClient;
    private HouseTaskRepository houseTaskRepository;

    @PostMapping("/tasks/{ruleId}")
    public TaskDto addRule(@PathVariable UUID ruleId) {

        return null;
    }

}
