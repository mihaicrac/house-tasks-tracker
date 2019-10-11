package com.house.tasktracker.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity(name = "house_tasks")
public class HouseTask {
    @Id
    private UUID id;

    private UUID ruleId;

    private UUID userId;

    private Instant claimedTimestamp;

    private Instant checkedTimestamp;

    @Enumerated(EnumType.STRING)
    private Status status;
}
