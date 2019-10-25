package com.house.taskstracker.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.house.taskstracker.authentication.domain.User;
import com.house.taskstracker.authentication.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public UserDto addUser(HttpServletRequest request, @RequestBody UserAddRequest regReq) {

        User u = new User();
        u.setEmail(regReq.getEmail());
        u.setEnabled(true);
        u.setFirstname(regReq.getFirstname());
        u.setLastname(regReq.getLastname());
        u.setPassword(passwordEncoder.encode(regReq.getPassword()));
        u.setId(UUID.randomUUID());
        u.setUsername(regReq.getUsername());
        userRepository.save(u);



        return userDto(u);
    }

    private UserDto userDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setFirstname(user.getFirstname());
        userDto.setLastname(user.getLastname());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
