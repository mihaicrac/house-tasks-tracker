package com.house.taskstracker.authentication.domain.usergroup;

import com.house.taskstracker.authentication.domain.group.Group;
import com.house.taskstracker.authentication.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, UserGroupKey> {
    List<UserGroup> findByUser(User user);
    List<UserGroup> findByGroup(Group group);
}
