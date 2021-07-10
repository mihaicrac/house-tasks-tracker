package com.house.tasktracker.infrastructure;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "tasks-rules")
public interface TasksRulesClient {

    @GetMapping("/order-rules/{id}")
    OrderRuleDto getRule(@RequestHeader("user-id") UUID userId, @PathVariable("id") UUID id);

    @Data
    class OrderRuleDto {

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
