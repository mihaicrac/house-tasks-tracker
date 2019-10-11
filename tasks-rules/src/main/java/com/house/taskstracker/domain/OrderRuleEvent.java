package com.house.taskstracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OrderRuleEvent {
    private Instant when;

    private UUID doneBy;

    private UUID orderRule;

}
