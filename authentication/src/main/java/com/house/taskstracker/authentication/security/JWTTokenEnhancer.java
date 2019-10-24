package com.house.taskstracker.authentication.security;

import com.house.taskstracker.authentication.domain.User;
import com.house.taskstracker.authentication.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class JWTTokenEnhancer implements TokenEnhancer {
    @Autowired
    private UserRepository userRepository;

    private String geId(String userName) {
        User user = userRepository.findByUsername(userName).get();
        return user.getId().toString();
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        String userId = geId(authentication.getName());

        additionalInfo.put("userId", userId);

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;
    }
}
