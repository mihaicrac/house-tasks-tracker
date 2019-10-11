package com.house.tasktracker.controller;

import lombok.Data;

import java.util.UUID;

@Data
public class AddTaskCommand {
    private UUID ruleId;
    private UUID userId;
}
