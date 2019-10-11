package com.house.taskstracker.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.house.taskstracker.user.JwtAuthenticationRequest;
import com.house.taskstracker.user.JwtTokenUtil;
import com.house.taskstracker.user.JwtUser;
import com.house.taskstracker.user.domain.UserRepository;
import com.house.taskstracker.user.service.JwtAuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@RestController
public class AuthenticationRestController {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationRestController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepo;

    @RequestMapping(value = "/api/${jwt.route.authentication.path}", method = RequestMethod.GET)
    public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request) {

        JwtAuthenticationRequest authenticationRequest;
        try {
            String credentials = new String(Base64.getDecoder().decode(request.getHeader("Authorization")));
            ObjectMapper objectMapper = new ObjectMapper();
            authenticationRequest = objectMapper.readValue(credentials, JwtAuthenticationRequest.class);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error decoding Authorization Header", e);
            throw new AccessDeniedException("Error getting Authorization Header", e);
        } catch (IOException e) {
            LOGGER.error("Error parsing Authorization Header as JwtAuthenticationRequest", e);
            throw new AccessDeniedException("Error getting Authorization Header", e);
        } catch (Exception e) {
            LOGGER.error("Error getting Authorization Header", e);
            throw new AccessDeniedException("Error getting Authorization Header", e);
        }

        userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        // Perform the security
        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Reload password post-security so we can generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            final String token = jwtTokenUtil.generateToken((JwtUser) userDetails);

            // Return the token
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (AuthenticationException e) {
            LOGGER.error("Failed to authenticate the request: Bad credentials");
            throw new AccessDeniedException("Failed to authenticate the request: Bad credentials", e);
        }
    }

}
