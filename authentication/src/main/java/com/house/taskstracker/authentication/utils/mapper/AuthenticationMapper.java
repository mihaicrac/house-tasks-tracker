package com.house.taskstracker.authentication.utils.mapper;

import com.house.taskstracker.authentication.domain.group.Group;
import com.house.taskstracker.authentication.domain.user.User;
import com.house.taskstracker.authentication.dto.GroupDto;
import com.house.taskstracker.authentication.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationMapper extends ModelMapper {


    public AuthenticationMapper() {
        super();

        createTypeMap(User.class, UserDto.class);
        {
            TypeMap<UserDto, User> type = createTypeMap(UserDto.class, User.class);
            type.addMappings(mapper -> {
                mapper.skip(User::setGroups);
                mapper.skip(User::setPassword);
                mapper.skip(User::setEnabled);
            });

        }
        createTypeMap(Group.class, GroupDto.class);
        {
            TypeMap<GroupDto, Group> type = createTypeMap(GroupDto.class, Group.class);
            type.addMappings(mapper ->
                    mapper.skip(Group::setUsers)
            );
        }
        validate();
    }

}
