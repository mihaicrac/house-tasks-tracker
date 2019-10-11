package com.house.tasktracker.infrastructure;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Component
public class TasksRulesClient {

    private RestTemplate restTemplate;
    public final String TASKS_RULES;

    public TasksRulesClient(@Value("${tasks-rules-service.order-rules}") String tasksRules) {
        restTemplate = new RestTemplate();
        TASKS_RULES = tasksRules;
    }

    public OrderRuleDto getOrderRule(UUID ruleId) {
        return restTemplate.getForEntity(TASKS_RULES + "/" + ruleId, OrderRuleDto.class).getBody();
    }

    @Data
    public static class OrderRuleDto {

        private UUID id;

        private List<OrderRuleItemDto> orderRuleItems;

        private int offset;

        @Data
        public static class OrderRuleItemDto {

            private UUID userId;

            private Integer orderId;

            private int outOfOrderOccurrences;

        }

    }
}
