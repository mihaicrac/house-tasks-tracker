package com.house.taskstracker.controller;

import lombok.Data;

import java.util.UUID;

@Data
public class AddOrderRuleEventCommand {
    private UUID orderRuleId;
    private UUID userId;
}
