package com.house.taskstracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRuleRepository extends JpaRepository<OrderRule, UUID> {
    List<OrderRule> findByGroupId(UUID groupId);

    @Query("select r from OrderRule r JOIN r.orderRuleItems i WHERE i.userId = :userId ")
    List<OrderRule> findByUserId(UUID userId);

}
