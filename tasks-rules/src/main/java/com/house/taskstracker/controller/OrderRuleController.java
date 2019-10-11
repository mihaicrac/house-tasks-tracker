package com.house.taskstracker.controller;

import com.house.taskstracker.application.OrderRuleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@AllArgsConstructor
@RestController
public class OrderRuleController {

    private OrderRuleService orderRuleService;
    private DtoTransformer dtoTransformer;

    @PostMapping("/order-rules")
    public OrderRuleDto addRule(@RequestBody AddOrderRuleCommand command) {
        return dtoTransformer.toOrderRuleDto(orderRuleService.addOrderRule(command));
    }

    @GetMapping("/order-rules/{id}")
    public OrderRuleDto getRule(@PathVariable("id") UUID id) {
        return dtoTransformer.toOrderRuleDto(orderRuleService.getOrderRule(id));
    }
}
