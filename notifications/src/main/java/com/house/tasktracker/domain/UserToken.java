package com.house.tasktracker.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity(name = "user_tokens")
public class UserToken {
    @Id
    private UUID id;

    private String token;

    public UserToken() {
    }
}
