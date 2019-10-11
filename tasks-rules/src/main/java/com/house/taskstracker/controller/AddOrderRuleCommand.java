package com.house.taskstracker.controller;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class AddOrderRuleCommand {
    List<OrderRuleItemDto> orderRuleItems;

    @Data
    public static class OrderRuleItemDto {
        private UUID userId;

        private Integer orderId;

    }

}


