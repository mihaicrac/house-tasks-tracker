package com.house.taskstracker.zuulsvr.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {"/api/authentication/oauth/login",
            "/api/authentication/oauth/check_token","/api/authentication/oauth/token", "/api/authentication/users"};

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated();
    }
}
