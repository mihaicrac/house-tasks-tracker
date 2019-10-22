package com.house.taskstracker.authentication;

import com.house.taskstracker.authentication.domain.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
public class AuthenticationApplication {

    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}