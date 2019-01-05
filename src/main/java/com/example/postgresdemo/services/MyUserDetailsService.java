package com.example.postgresdemo.services;

import com.example.postgresdemo.model.User;
import com.example.postgresdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getActive(),user.getActive(),user.getActive(),user.getActive(), AuthorityUtils.createAuthorityList(
                user.getRoles()
                        .stream()
                        .map(r -> "ROLE_" + r.getName().toUpperCase())
                        .collect(Collectors.toList())
                        .toArray(new String[]{})));
    }



}
