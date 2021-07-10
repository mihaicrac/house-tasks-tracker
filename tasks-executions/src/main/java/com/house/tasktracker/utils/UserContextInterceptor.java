package com.house.tasktracker.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserContextInterceptor implements RequestInterceptor {
    
    @Override
    public void apply(RequestTemplate template) {
        if(UserContext.getUserId() != null) {
            template.header(UserContext.USER_ID, UserContext.getUserId().toString());
        }
    }
}