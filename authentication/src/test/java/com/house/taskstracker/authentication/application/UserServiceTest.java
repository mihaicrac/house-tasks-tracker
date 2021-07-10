package com.house.taskstracker.authentication.application;

import com.house.taskstracker.authentication.DataSourceConfig;
import com.house.taskstracker.authentication.DtoFactory;
import com.house.taskstracker.authentication.domain.user.User;
import com.house.taskstracker.authentication.domain.user.UserRepository;
import com.house.taskstracker.authentication.dto.UserDto;
import com.house.taskstracker.authentication.utils.mapper.AuthenticationMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, UserServiceTest.Configuration.class,
        UserService.class, AuthenticationMapper.class})
public class UserServiceTest {

    static class Configuration {
        @Bean
        public PasswordEncoder passwordEncoder() {
            String idForEncode = "bcrypt";
            Map encoders = new HashMap<>();
            encoders.put(idForEncode, new BCryptPasswordEncoder());
            encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
            encoders.put("scrypt", new SCryptPasswordEncoder());

            return new DelegatingPasswordEncoder(idForEncode, encoders);
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAddUser() {
        UserDto userDto = DtoFactory.createUserDto();
        UserDto user1 = userService.createUser(userDto);
        User user = userRepository.findByUsername(userDto.getUsername()).get();
        Assert.assertTrue(passwordEncoder.matches(userDto.getPassword(), user.getPassword()));
        Assert.assertNotNull(user.getId());

        userRepository.deleteAll();
    }

}
