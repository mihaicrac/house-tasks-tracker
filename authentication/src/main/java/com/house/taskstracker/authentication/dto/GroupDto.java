package com.house.taskstracker.authentication.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GroupDto {
    private UUID id;

    private String name;

}
