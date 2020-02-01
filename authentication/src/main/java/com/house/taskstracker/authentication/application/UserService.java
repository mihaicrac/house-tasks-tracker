package com.house.taskstracker.authentication.application;

import com.house.taskstracker.authentication.domain.user.User;
import com.house.taskstracker.authentication.domain.user.UserRepository;
import com.house.taskstracker.authentication.dto.UserDto;
import com.house.taskstracker.authentication.utils.mapper.AuthenticationMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;
    private AuthenticationMapper authenticationMapper;
    private PasswordEncoder passwordEncoder;

    public UserDto createUser(UserDto userDto) {
        User user = User.createUser(userDto);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setGroups(Collections.emptySet());
        userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
            throw new RuntimeException();
        });
        return authenticationMapper.map(userRepository.save(user), UserDto.class);
    }

}
