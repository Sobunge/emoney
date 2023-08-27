package com.pensasha.emoney.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pensasha.emoney.enums.Role;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {

        Optional<User> currentUser = userRepository.findById(Integer.parseInt(username));
        List<String> stringRole = new ArrayList<String>();

        for (Role role : currentUser.get().getRoles()) {
            stringRole.add(role.name());
        }

        currentUser.orElseThrow(() -> new UsernameNotFoundException("Username: " + username + " not found."));

        UserDetails user = new org.springframework.security.core.userdetails.User(username,
                currentUser.get().getPassword(),
                true, true, true, true, AuthorityUtils.createAuthorityList(stringRole.toString()));

        return user;
    }

}
