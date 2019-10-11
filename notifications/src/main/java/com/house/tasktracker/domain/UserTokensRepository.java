package com.house.tasktracker.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserTokensRepository extends JpaRepository<UserToken, UUID> {

    List<UserToken> findByIdIn(List<UUID> userIds);

}
