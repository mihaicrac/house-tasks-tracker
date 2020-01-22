package com.house.taskstracker.authentication;

import com.house.taskstracker.authentication.domain.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
public class AuthenticationApplication {

    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
    }

}
