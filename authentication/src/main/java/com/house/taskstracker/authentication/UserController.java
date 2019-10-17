package com.house.taskstracker.authentication;

import com.house.taskstracker.authentication.domain.User;
import com.house.taskstracker.authentication.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public UUID addUser(@RequestBody UserAddRequest regReq) {

        User u = new User();
        u.setEmail(regReq.getEmail());
        u.setEnabled(true);
        u.setFirstname(regReq.getFirstname());
        u.setLastname(regReq.getLastname());
        u.setPassword(regReq.getPassword());
        u.setId(UUID.randomUUID());

        userRepository.save(u);

        // Return the token
        return UUID.randomUUID();
    }

}
