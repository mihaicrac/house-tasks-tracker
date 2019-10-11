package com.house.taskstracker.controller;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRuleDto {

    private UUID id;

    private List<OrderRuleItemDto> orderRuleItems;

    private int offset;

    @Data
    static class OrderRuleItemDto {

        private UUID userId;

        private Integer orderId;

        private int outOfOrderOccurrences;

    }

}