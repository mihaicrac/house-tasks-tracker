package com.house.tasktracker.infrastructure;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "notifications-service")
public interface NotificationsClient {

    @PostMapping("/notifications")
    void sendNotifications(SendNotificationCommand sendNotificationCommand);

    @Data
    class SendNotificationCommand {
        private UUID taskExecutionId;
        private UUID ruleId;
        private UUID senderUser;
    }
}
