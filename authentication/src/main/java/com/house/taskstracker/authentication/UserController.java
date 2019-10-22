package com.house.taskstracker.authentication;

import com.house.taskstracker.authentication.domain.User;
import com.house.taskstracker.authentication.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public UUID addUser(@RequestBody UserAddRequest regReq) {

        User u = new User();
        u.setEmail(regReq.getEmail());
        u.setEnabled(true);
        u.setFirstname(regReq.getFirstname());
        u.setLastname(regReq.getLastname());
        u.setPassword(passwordEncoder.encode(regReq.getPassword()));
        u.setId(UUID.randomUUID());
        u.setUsername(regReq.getUsername());
        userRepository.save(u);

        // Return the token
        return UUID.randomUUID();
    }

    @RequestMapping(value =  "/user" , produces = "application/json")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }

}
