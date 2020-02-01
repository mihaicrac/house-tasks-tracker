package com.house.taskstracker.authentication.domain.usergroup;

import com.house.taskstracker.authentication.domain.group.Group;
import com.house.taskstracker.authentication.domain.user.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users_groups")
public class UserGroup {

    @EmbeddedId
    private UserGroupKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("group_id")
    @JoinColumn(name = "group_id")
    private Group group;

    private boolean admin;

}
