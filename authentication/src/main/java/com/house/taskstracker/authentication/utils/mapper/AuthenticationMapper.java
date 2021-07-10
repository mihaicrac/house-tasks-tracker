package com.house.taskstracker.authentication.utils.mapper;

import com.house.taskstracker.authentication.domain.group.Group;
import com.house.taskstracker.authentication.domain.user.User;
import com.house.taskstracker.authentication.domain.usergroup.UserGroup;
import com.house.taskstracker.authentication.dto.GroupDto;
import com.house.taskstracker.authentication.dto.UserDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class AuthenticationMapper extends ModelMapper {


    public AuthenticationMapper() {
        super();

        {
            TypeMap<User, UserDto> type = createTypeMap(User.class, UserDto.class);
            type.addMappings(mapper -> mapper.skip(UserDto::setPassword));
        }

        {
            TypeMap<UserDto, User> type = createTypeMap(UserDto.class, User.class);
            type.addMappings(mapper -> {
                mapper.skip(User::setGroups);
                mapper.skip(User::setPassword);
                mapper.skip(User::setEnabled);
            });

        }

        {
            TypeMap<Group, GroupDto> type = createTypeMap(Group.class, GroupDto.class);
            Converter<Set<UserGroup>, List<UserDto>> converter = (context) -> context.getSource().stream().map(ug -> map(ug.getUser(), UserDto.class)).collect(Collectors.toList());
            type.addMappings(mapper ->
                    mapper.using(converter).map(Group::getUsers, GroupDto::setUsers)
            );
        }

        {
            TypeMap<GroupDto, Group> type = createTypeMap(GroupDto.class, Group.class);
            type.addMappings(mapper ->
                    mapper.skip(Group::setUsers)
            );
        }
        validate();
    }

}
