package com.house.taskstracker.authentication.application;

import com.house.taskstracker.authentication.DataSourceConfig;
import com.house.taskstracker.authentication.DtoFactory;
import com.house.taskstracker.authentication.domain.group.Group;
import com.house.taskstracker.authentication.domain.group.GroupRepository;
import com.house.taskstracker.authentication.domain.user.UserRepository;
import com.house.taskstracker.authentication.domain.usergroup.UserGroup;
import com.house.taskstracker.authentication.domain.usergroup.UserGroupKey;
import com.house.taskstracker.authentication.domain.usergroup.UserGroupRepository;
import com.house.taskstracker.authentication.dto.GroupDto;
import com.house.taskstracker.authentication.dto.UserDto;
import com.house.taskstracker.authentication.utils.mapper.AuthenticationMapper;
import org.junit.After;
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
import java.util.UUID;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, GroupServiceTest.Configuration.class,
        UserService.class, GroupService.class, AuthenticationMapper.class})
public class GroupServiceTest {

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
    private GroupService groupService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testAddGroup() {
        UserGroup userGroup = addGroup();
        Assert.assertNotNull(userGroup.getGroup().getId());
        Assert.assertTrue(userGroup.isAdmin());
    }

    @Test
    public void leaveGroup() {
        UserGroup userGroup = addGroup();

        UUID userId = userGroup.getUser().getId();
        UUID groupId = userGroup.getGroup().getId();

        groupService.leaveGroup(userId, groupId);

        Assert.assertFalse(userGroupRepository.findById(new UserGroupKey(groupId, userId)).isPresent());
        Assert.assertTrue(userRepository.findById(userId).isPresent());
        Assert.assertTrue(groupRepository.findById(groupId).isPresent());
    }

    private UserGroup addGroup() {
        UserDto userDto = DtoFactory.createUserDto();
        UUID userId = userService.createUser(userDto).getId();

        GroupDto groupDto = DtoFactory.createGroupDto();
        groupService.addGroup(userId, groupDto);

        Group group = groupRepository.findByName(groupDto.getName()).get();
        UserGroup userGroup = userGroupRepository.findById(new UserGroupKey(group.getId(), userId)).get();
        return userGroup;
    }

    @After
    public void cleanUp() {
        userGroupRepository.deleteAll();
        userRepository.deleteAll();
        groupRepository.deleteAll();
    }


}
