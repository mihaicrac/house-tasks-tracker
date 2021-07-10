package com.house.taskstracker.controller;

import com.house.taskstracker.application.OrderRuleService;
import com.house.taskstracker.utils.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class OrderRuleEventsController {

    private OrderRuleService orderRuleService;
    private DtoTransformer dtoTransformer;

    @PostMapping("/order-rule-events/{ruleId}")
    public OrderRuleDto addRule(@PathVariable UUID ruleId) {
        AddOrderRuleEventCommand command = new AddOrderRuleEventCommand(ruleId, UserContext.getUserId());
        return dtoTransformer.toOrderRuleDto(orderRuleService.addOrderRuleEvent(command));
    }
}
