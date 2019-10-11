package com.house.taskstracker.controller;

import com.house.taskstracker.application.OrderRuleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class OrderRuleEventsController {

    private OrderRuleService orderRuleService;
    private DtoTransformer dtoTransformer;

    @PostMapping("/order-rule-events")
    public OrderRuleDto addRule(@RequestBody AddOrderRuleEventCommand command) {
        return dtoTransformer.toOrderRuleDto(orderRuleService.addOrderRuleEvent(command));
    }
}
