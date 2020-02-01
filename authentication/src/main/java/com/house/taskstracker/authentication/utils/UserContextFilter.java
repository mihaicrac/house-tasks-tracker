package com.house.taskstracker.authentication.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserContextFilter implements Filter {


    @Autowired
    private JwtTokenStore tokenStore;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {


        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        System.out.println("****** I am entering the licensing service id with auth token: " + httpServletRequest.getHeader("Authorization"));


        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));

        String token = httpServletRequest.getHeader(UserContext.AUTH_TOKEN);

        getUserId(token).ifPresent(v ->
                UserContextHolder.getContext().setUserId(v)
        );
        UserContextHolder.getContext().setAuthToken(token);
        UserContextHolder.getContext().setOrgId(httpServletRequest.getHeader(UserContext.ORG_ID));

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    private Optional<UUID> getUserId(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            OAuth2AccessToken decodedToken = tokenStore.readAccessToken(token.replace("Bearer ", ""));
            Map<String, Object> details = decodedToken.getAdditionalInformation();
            return Optional.of(UUID.fromString((String) details.get("userId")));
        }
        return Optional.empty();
    }
}