package org.example.licenta.db.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "authentication")
public class AuthenticationEntity implements UserDetails {
    @Id
    private String userId;

    private String userPassword;

    private String userName;

    private String userFirstName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    private Set<RoleEntity> authorities;

    public AuthenticationEntity() {
        super();
        this.authorities = new HashSet<RoleEntity>();
    }

    public AuthenticationEntity(String userId, String userPassword, String userName, String userFirstName, Set<RoleEntity> authorities) {
        super();
        this.userId = userId;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userFirstName = userFirstName;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userFirstName + " " + this.userName;
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
        return true;
    }
}
