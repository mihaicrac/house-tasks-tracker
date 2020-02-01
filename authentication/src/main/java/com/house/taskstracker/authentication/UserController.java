package com.house.taskstracker.authentication;

import com.house.taskstracker.authentication.application.UserService;
import com.house.taskstracker.authentication.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public UserDto addUser(@RequestBody UserDto regReq) {
        return userService.createUser(regReq);
    }
}
