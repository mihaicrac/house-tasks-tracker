package com.house.tasktracker.controller;

import lombok.Data;

import java.util.UUID;

@Data
public class RegisterTokenCommand {

    private UUID userId;
    private String token;
}
