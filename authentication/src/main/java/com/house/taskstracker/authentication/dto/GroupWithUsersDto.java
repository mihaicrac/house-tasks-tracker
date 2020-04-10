package com.house.taskstracker.authentication.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupWithUsersDto extends GroupDto {
    private List<UserDto> users;
}
