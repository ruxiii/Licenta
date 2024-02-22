//package org.example.licenta.authentication;
//
//import org.example.licenta.db.entities.AuthenticationEntity;
//import org.example.licenta.db.entities.UserEntity;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//
//@ComponentScan
//public class CustomUserDetails implements UserDetails {
//
//    private AuthenticationEntity authenticationEntity;
//
//    public CustomUserDetails(AuthenticationEntity authenticationEntity) {
//        super();
//        this.authenticationEntity = authenticationEntity;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(authenticationEntity.getUserRole())));
//    }
//
//    @Override
//    public String getPassword() {
//        return authenticationEntity.getUserPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return authenticationEntity.getUserId();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
