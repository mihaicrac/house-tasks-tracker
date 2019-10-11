package com.house.tasktracker.infrastructure;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class NotificationsClient {

    private RestTemplate restTemplate;
    public final String NOTIFICATIONS;

    public NotificationsClient(@Value("${notifications-service.notifications}") String tasksRules) {
        restTemplate = new RestTemplate();
        NOTIFICATIONS = tasksRules;
    }

    public void sendNotifications(SendNotificationCommand sendNotificationCommand) {
        HttpEntity<SendNotificationCommand> request = new HttpEntity<>(sendNotificationCommand);
        restTemplate.exchange(NOTIFICATIONS, HttpMethod.POST, request, Void.class);
    }

    @Data
    public static class SendNotificationCommand {
        private UUID ruleId;
        private UUID senderUser;
    }
}
