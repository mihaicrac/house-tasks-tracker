package com.house.taskstracker.authentication.application;

import com.house.taskstracker.authentication.domain.group.Group;
import com.house.taskstracker.authentication.domain.group.GroupRepository;
import com.house.taskstracker.authentication.domain.user.User;
import com.house.taskstracker.authentication.domain.user.UserRepository;
import com.house.taskstracker.authentication.domain.usergroup.UserGroup;
import com.house.taskstracker.authentication.domain.usergroup.UserGroupKey;
import com.house.taskstracker.authentication.domain.usergroup.UserGroupRepository;
import com.house.taskstracker.authentication.dto.GroupDto;
import com.house.taskstracker.authentication.dto.UserDto;
import com.house.taskstracker.authentication.utils.mapper.AuthenticationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GroupService {
    private GroupRepository groupRepository;
    private AuthenticationMapper authenticationMapper;
    private UserGroupRepository userGroupRepository;
    private UserRepository userRepository;

    @Transactional
    public GroupDto addGroup(UUID creatorUser, GroupDto groupDto) {
        userRepository.findById(creatorUser).orElseThrow(RuntimeException::new);
        Group group = Group.createGroup(groupDto);
        group.setId(UUID.randomUUID());
        groupRepository.save(group);
        UserGroup userGroup = joinGroup(creatorUser, group.getId());
        userGroup.setAdmin(true);
        return authenticationMapper.map(group, GroupDto.class);
    }

    @Transactional
    public UserGroup joinGroup(UUID userId, UUID groupId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Group group = groupRepository.findById(groupId).orElseThrow(RuntimeException::new);

        UserGroup userGroup = new UserGroup();
        userGroup.setId(new UserGroupKey(groupId, userId));
        userGroup.setUser(user);
        userGroup.setGroup(group);

        group.addUser(userGroup);
        user.addGroup(userGroup);

        return userGroupRepository.save(userGroup);
    }

    @Transactional
    public void leaveGroup(UUID userId, UUID groupId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        Group group = groupRepository.findById(groupId).orElseThrow(RuntimeException::new);

        UserGroup userGroup = userGroupRepository.findById(new UserGroupKey(groupId, userId)).orElseThrow(RuntimeException::new);

        user.deleteGroup(userGroup);
        group.deleteUser(userGroup);
        userGroupRepository.delete(userGroup);
    }

    public List<GroupDto> getGroupsByUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        List<UserGroup> groups = userGroupRepository.findByUser(user);
        return groups.stream().map(v -> authenticationMapper.map(v.getGroup(), GroupDto.class)).collect(Collectors.toList());
    }

    public List<UserDto> getUsersByGroup(UUID groupId) {
        Group group = groupRepository.findById(groupId).orElseThrow(RuntimeException::new);
        List<UserGroup> users = userGroupRepository.findByGroup(group);
        return users.stream().map(u -> authenticationMapper.map(u.getUser(), UserDto.class)).collect(Collectors.toList());
    }

}
