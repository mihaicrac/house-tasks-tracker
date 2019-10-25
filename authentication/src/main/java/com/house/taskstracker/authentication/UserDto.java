package com.house.taskstracker.authentication;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;
}
