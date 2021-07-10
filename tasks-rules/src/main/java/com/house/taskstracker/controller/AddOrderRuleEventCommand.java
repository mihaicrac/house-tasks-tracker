package com.house.taskstracker.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddOrderRuleEventCommand {
    private UUID orderRuleId;
    private UUID userId;
}
