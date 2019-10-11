package com.house.tasktracker.controller;

import com.house.tasktracker.domain.HouseTask;
import com.house.tasktracker.domain.HouseTaskRepository;
import com.house.tasktracker.domain.Status;
import com.house.tasktracker.infrastructure.NotificationsClient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class TasksController {

    private NotificationsClient notificationsClient;
    private HouseTaskRepository houseTaskRepository;

    @PostMapping("/tasks")
    public TaskDto addRule(@RequestBody AddTaskCommand command) {
        TaskDto task = toTaskDto(houseTaskRepository.save(toHouseTask(command)));
        notificationsClient.sendNotifications(toSendNotificationCommand(command));
        return task;
    }

    private HouseTask toHouseTask(AddTaskCommand command) {
        HouseTask task = new HouseTask();
        task.setId(UUID.randomUUID());
        task.setRuleId(command.getRuleId());
        task.setUserId(command.getUserId());
        task.setClaimedTimestamp(Instant.now());
        task.setStatus(Status.CLAIMED);
        return task;
    }

    private TaskDto toTaskDto(HouseTask task) {
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setClaimedTime(task.getClaimedTimestamp());
        dto.setRuleId(task.getRuleId());
        dto.setUserId(task.getUserId());
        dto.setStatus(task.getStatus());
        return dto;
    }

    private NotificationsClient.SendNotificationCommand toSendNotificationCommand(AddTaskCommand command) {
        NotificationsClient.SendNotificationCommand sendNotificationCommand = new NotificationsClient.SendNotificationCommand();
        sendNotificationCommand.setRuleId(command.getRuleId());
        sendNotificationCommand.setSenderUser(command.getUserId());
        return sendNotificationCommand;
    }

}
