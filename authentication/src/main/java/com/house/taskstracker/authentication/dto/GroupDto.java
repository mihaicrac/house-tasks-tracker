package com.house.taskstracker.authentication.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GroupDto {
    private UUID id;

    private String name;

    private List<UserDto> users;

}
