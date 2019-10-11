package com.house.tasktracker.controller;

import com.house.tasktracker.domain.Status;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class TaskDto {
    private UUID id;
    private UUID ruleId;
    private UUID userId;
    private Instant claimedTime;
    private Instant checkedTime;
    private Status status;
}
