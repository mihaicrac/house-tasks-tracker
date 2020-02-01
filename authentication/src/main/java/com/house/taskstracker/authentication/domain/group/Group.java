package com.house.taskstracker.authentication.domain.group;

import com.house.taskstracker.authentication.domain.usergroup.UserGroup;
import com.house.taskstracker.authentication.dto.GroupDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "groups")
public class Group {
    @Id
    private UUID id;

    @EqualsAndHashCode.Include
    private String name;

    @OneToMany(mappedBy = "group")
    @ToString.Exclude
    private Set<UserGroup> users = new HashSet<>();

    public static Group createGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setName(groupDto.getName());
        return group;
    }

    public void addUser(UserGroup user) {
        users.add(user);
    }

    public void deleteUser(UserGroup user) {
        users.remove(user);
    }

}
