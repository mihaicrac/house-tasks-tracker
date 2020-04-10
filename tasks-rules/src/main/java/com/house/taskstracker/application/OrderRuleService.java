package com.house.taskstracker.application;

import com.house.taskstracker.controller.AddOrderRuleCommand;
import com.house.taskstracker.controller.AddOrderRuleEventCommand;
import com.house.taskstracker.domain.OrderRule;
import com.house.taskstracker.domain.OrderRuleEvent;
import com.house.taskstracker.domain.OrderRuleItem;
import com.house.taskstracker.domain.OrderRuleRepository;
import com.house.taskstracker.infrastructure.OrderOffsetChangedSource;
import lombok.AllArgsConstructor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderRuleService {

    private OrderRuleRepository orderRuleRepository;
    private OrderOffsetChangedSource events;

    public OrderRule addOrderRule(AddOrderRuleCommand command) {
        return orderRuleRepository.save(toOrderRule(command));
    }

    @Transactional
    public OrderRule addOrderRuleEvent(AddOrderRuleEventCommand command) {
        OrderRule rule = orderRuleRepository.findById(command.getOrderRuleId()).get();
        rule.moveOffset(command.getUserId());
        OrderRuleEvent event = new OrderRuleEvent(Instant.now(), command.getUserId(), command.getOrderRuleId());
        events.output().send(MessageBuilder.withPayload(event).build());
        return rule;
    }

    public OrderRule getOrderRule(UUID id) {
        return orderRuleRepository.findById(id).get();
    }

    private OrderRule toOrderRule(AddOrderRuleCommand command) {
        OrderRule rule = new OrderRule();
        rule.setName(command.getName());
        rule.setGroupId(command.getGroupId());
        rule.setOrderRuleItems(command.getOrderRuleItems().stream().map(this::toOrderRuleItem).collect(Collectors.toList()));
        rule.setId(UUID.randomUUID());
        return rule;
    }

    private OrderRuleItem toOrderRuleItem(AddOrderRuleCommand.OrderRuleItemDto dto) {
        OrderRuleItem item = new OrderRuleItem();
        item.setUserId(dto.getUserId());
        item.setOrderId(dto.getOrderId());
        return item;
    }

}
