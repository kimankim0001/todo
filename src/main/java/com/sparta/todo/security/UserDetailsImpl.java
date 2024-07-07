package com.sparta.todo.security;

import com.sparta.todo.entity.User;
import com.sparta.todo.enums.Auth;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Auth userAuth = user.getAuth();
        String auth = userAuth.getUser();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(auth);
        Collection<GrantedAuthority> auths = new ArrayList<>();
        auths.add(simpleGrantedAuthority);
        return auths;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
