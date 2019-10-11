package com.house.taskstracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRuleRepository extends JpaRepository<OrderRule, UUID> {
}
