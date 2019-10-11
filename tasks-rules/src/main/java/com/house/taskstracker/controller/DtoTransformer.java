package com.house.taskstracker.controller;

import com.house.taskstracker.domain.OrderRule;
import com.house.taskstracker.domain.OrderRuleItem;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DtoTransformer {
    public OrderRuleDto toOrderRuleDto(OrderRule rule) {
        OrderRuleDto dto = new OrderRuleDto();
        dto.setOrderRuleItems(rule.getOrderRuleItems()
                .stream()
                .map(this::toOrderRuleItemDto)
                .collect(Collectors.toList()));
        dto.setId(rule.getId());
        dto.setOffset(rule.getOffset());
        return dto;
    }

    private OrderRuleDto.OrderRuleItemDto toOrderRuleItemDto(OrderRuleItem item) {
        OrderRuleDto.OrderRuleItemDto dto = new OrderRuleDto.OrderRuleItemDto();
        dto.setOrderId(item.getOrderId());
        dto.setUserId(item.getUserId());
        dto.setOutOfOrderOccurrences(item.getOutOfOrderOccurrences());
        return dto;
    }
}
