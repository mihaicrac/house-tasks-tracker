package com.house.tasktracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HouseTaskRepository extends JpaRepository<HouseTask, UUID> {

    Optional<HouseTask> findByRuleIdAndUserIdAndStatusOrderByClaimedTimestampDesc(UUID ruleId, UUID userId, Status status);

}
