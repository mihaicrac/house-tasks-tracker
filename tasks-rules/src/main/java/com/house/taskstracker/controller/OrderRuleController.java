package com.house.taskstracker.controller;

import com.house.taskstracker.application.OrderRuleService;
import com.house.taskstracker.utils.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class OrderRuleController {

    private OrderRuleService orderRuleService;


    @PostMapping("/order-rules")
    public OrderRuleDto addRule(@RequestBody AddOrderRuleCommand command) {
        return orderRuleService.addOrderRule(command);
    }

    @GetMapping("/order-rules/{id}")
    public OrderRuleDto getRule(@PathVariable("id") UUID id) {
        return orderRuleService.getOrderRule(id);
    }

    @GetMapping("/order-rules")
    public List<OrderRuleDto> getRules(@RequestParam(name = "groupId", required = true) UUID groupId) {
        return orderRuleService.getOrderRulesByGroupId(groupId);
    }

    @GetMapping("/order-rules/user")
    public List<OrderRuleDto> getRules() {
        UUID userId = UserContext.getUserId();
        return orderRuleService.getOrderRulesByUserId(userId);
    }
}
