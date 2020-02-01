package com.house.taskstracker.authentication.domain.user;

import com.house.taskstracker.authentication.domain.usergroup.UserGroup;
import com.house.taskstracker.authentication.dto.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    private UUID id;

    @EqualsAndHashCode.Include
    @Column(name = "username", length = 40, unique = true, nullable = false)
    @NotNull
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "firstname", length = 40)
    @NotNull
    @Size(min = 4, max = 50)
    private String firstname;

    @Column(name = "lastname", length = 40)
    @NotNull
    @Size(min = 4, max = 50)
    private String lastname;

    @Column(name = "email", length = 254, unique = true, nullable = false)
    @NotNull
    @Size(min = 4, max = 50)
    private String email;

    @Column(name = "password", length = 60, nullable = false)
    @NotNull
    @Size(min = 4, max = 100)
    private String password;

    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<UserGroup> groups = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static User createUser(UserDto regReq) {
        User u = new User();
        u.setEmail(regReq.getEmail());
        u.setFirstname(regReq.getFirstName());
        u.setLastname(regReq.getLastName());
        u.setId(UUID.randomUUID());
        u.setUsername(regReq.getUsername());
        return u;
    }

    public void addGroup(UserGroup group){
        groups.add(group);
    }

    public void deleteGroup(UserGroup group){
        groups.remove(group);
    }
}