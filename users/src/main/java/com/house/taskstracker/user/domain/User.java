package com.house.taskstracker.user.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private UUID id;

    @Column(name = "username", length = 40, unique = true, nullable = false)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "password", length = 60, nullable = false)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "firstname", length = 40)
    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "lastname", length = 40)
    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "email", length = 254, unique = true, nullable = false)
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "enabled", columnDefinition = "BIT(1)", nullable = false)
    private Boolean enabled;
}