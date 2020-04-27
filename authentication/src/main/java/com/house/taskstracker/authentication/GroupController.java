package com.house.taskstracker.authentication;

import com.house.taskstracker.authentication.application.GroupService;
import com.house.taskstracker.authentication.dto.GroupDto;
import com.house.taskstracker.authentication.dto.UserDto;
import com.house.taskstracker.authentication.utils.UserContext;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class GroupController {
    private GroupService groupService;

    @PostMapping(value = "/groups")
    public GroupDto addGroup(@RequestBody GroupDto groupDto) {
        UUID userId = UserContext.getUserId();
        return groupService.addGroup(userId, groupDto);
    }

    @GetMapping(value = "/groups")
    public List<GroupDto> getGroups(@RequestParam(value = "name", required = false) String name) {
        UUID userId = UserContext.getUserId();
        if (name != null && !name.isEmpty()) {
            return groupService.getGroupsByName(name);
        }
        return groupService.getGroupsByUser(userId);
    }

    @GetMapping(value = "/groups/{id}/users")
    public List<UserDto> getUsers(@PathVariable UUID id) {
        return groupService.getUsersByGroup(id);
    }

    @PostMapping(value = "/groups/{id}/user")
    public void userJoinGroup(@PathVariable("id") UUID groupId) {
        UUID userId = UserContext.getUserId();
        groupService.joinGroup(userId, groupId);
    }

    @DeleteMapping(value = "/groups/{id}/user")
    public void userLeavesGroup(@PathVariable("id") UUID groupId) {
        UUID userId = UserContext.getUserId();
        groupService.leaveGroup(userId, groupId);
    }

}
