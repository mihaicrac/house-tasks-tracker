package com.house.tasktracker.controller;

import lombok.Data;

import java.util.UUID;

@Data
public class SendNotificationCommand {
    private UUID taskExecutionId;
    private UUID ruleId;
    private UUID senderUser;
}
