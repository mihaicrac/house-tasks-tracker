package com.house.taskstracker.authentication.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    @NotNull(message = "username required")
    @Size(min = 1, max = 40, message = "username '${validatedValue}' size must be between {min} and {max}")
    private String username;

    @Size(max = 40, message = "firstname '${validatedValue}' max size must be {max}")
    private String firstName;

    @Size(max = 40, message = "lastname '${validatedValue}' max size must be {max}")
    private String lastName;

    @NotNull(message = "password required")
    private String password;

    @NotNull(message = "email required")
    @Size(min = 1, max = 254, message = "email '${validatedValue}' size must be between {min} and {max}")
    private String email;

}
