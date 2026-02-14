package com.example.ecommerce.ecommerce.config;

import com.example.ecommerce.ecommerce.models.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;


public class UserInfoDetails implements UserDetails {

    private UserInfo userInfo;

    public UserInfoDetails(UserInfo userInfo)
    {
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(userInfo.getRoles().split(",")).map(role -> new SimpleGrantedAuthority("ROLE_" +role.trim())).toList();
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
