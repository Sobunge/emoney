package com.pensasha.emoney.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<User> currentUser = userRepository.findById(Integer.parseInt(username));

        currentUser.orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found."));

        UserDetails user = new org.springframework.security.core.userdetails.User(username, currentUser.get().getPassword(),
                true, true, true, true,
                AuthorityUtils.createAuthorityList(currentUser.get().getRole().name()));

        return user;
    }
}
