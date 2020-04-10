package com.house.taskstracker.controller;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddOrderRuleCommand {
    private List<OrderRuleItemDto> orderRuleItems;
    private String name;
    private UUID groupId;

    @Data
    public static class OrderRuleItemDto {
        private UUID userId;

        private Integer orderId;

    }

}


