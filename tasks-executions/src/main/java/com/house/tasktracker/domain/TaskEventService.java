package com.house.tasktracker.domain;

import com.house.tasktracker.infrastructure.NotificationsClient;
import com.house.tasktracker.infrastructure.OrderOffsetChangedSink;
import lombok.AllArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class TaskEventService {

    private HouseTaskRepository houseTaskRepository;
    private NotificationsClient notificationsClient;

    @StreamListener(OrderOffsetChangedSink.OFFSET_CHANGED_INPUT)
    @Transactional
    public void getEvent(TaskExecutionEvent event) {
        HouseTask task = houseTaskRepository.save(toHouseTask(event));
        notificationsClient.sendNotifications(toSendNotificationCommand(task));
    }

    private HouseTask toHouseTask(TaskExecutionEvent event) {
        HouseTask task = new HouseTask();
        task.setId(UUID.randomUUID());
        task.setRuleId(event.getOrderRule());
        task.setUserId(event.getDoneBy());
        task.setClaimedTimestamp(event.getWhen());
        task.setStatus(Status.CLAIMED);
        return task;
    }

    private NotificationsClient.SendNotificationCommand toSendNotificationCommand(HouseTask task) {
        NotificationsClient.SendNotificationCommand sendNotificationCommand = new NotificationsClient.SendNotificationCommand();
        sendNotificationCommand.setTaskExecutionId(task.getId());
        sendNotificationCommand.setRuleId(task.getRuleId());
        sendNotificationCommand.setSenderUser(task.getUserId());
        return sendNotificationCommand;
    }
}
