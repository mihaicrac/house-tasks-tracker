package com.house.taskstracker.application;

import com.house.taskstracker.controller.AddOrderRuleCommand;
import com.house.taskstracker.controller.AddOrderRuleEventCommand;
import com.house.taskstracker.controller.DtoTransformer;
import com.house.taskstracker.controller.OrderRuleDto;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderRuleService {

    private OrderRuleRepository orderRuleRepository;
    private OrderOffsetChangedSource events;
    private DtoTransformer dtoTransformer;

    public OrderRuleDto addOrderRule(AddOrderRuleCommand command) {
        return dtoTransformer.toOrderRuleDto(orderRuleRepository.save(toOrderRule(command)));
    }

    @Transactional
    public OrderRule addOrderRuleEvent(AddOrderRuleEventCommand command) {
        OrderRule rule = orderRuleRepository.findById(command.getOrderRuleId()).get();
        rule.moveOffset(command.getUserId());
        OrderRuleEvent event = new OrderRuleEvent(Instant.now(), command.getUserId(), command.getOrderRuleId());
        events.output().send(MessageBuilder.withPayload(event).build());
        return rule;
    }

    public OrderRuleDto getOrderRule(UUID id) {
        return dtoTransformer.toOrderRuleDto(orderRuleRepository.findById(id).get());
    }

    public List<OrderRuleDto> getOrderRules(UUID groupId) {
        return orderRuleRepository.findByGroupId(groupId).stream().map(g -> dtoTransformer.toOrderRuleDto(g)).collect(Collectors.toList());
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
