package com.house.taskstracker.authentication;

import com.house.taskstracker.authentication.dto.GroupDto;
import com.house.taskstracker.authentication.dto.UserDto;

public class DtoFactory {

    public static UserDto createUserDto() {
        UserDto userDto = new UserDto();
        userDto.setUsername("username");
        userDto.setEmail("email");
        userDto.setFirstName("firstName");
        userDto.setLastName("lastName");
        userDto.setPassword("password");
        return userDto;
    }

    public static GroupDto createGroupDto() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName("group1");
        return groupDto;
    }

}
