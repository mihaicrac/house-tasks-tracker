package com.house.taskstracker.authentication.domain.usergroup;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Data
@Embeddable
public class UserGroupKey implements Serializable {
    @Column(name = "group_id")
    private UUID groupId;

    @Column(name = "user_id")
    private UUID userId;

    public UserGroupKey(){}

    public UserGroupKey(UUID groupId, UUID userId) {
        this.groupId = groupId;
        this.userId = userId;
    }
}
