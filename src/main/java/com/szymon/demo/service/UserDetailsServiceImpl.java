package com.szymon.demo.service;

import com.szymon.demo.collections.User;
import com.szymon.demo.repository.UserRepository;
import com.szymon.demo.security.MyUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Credentials not exist in database. Can't log in.");
        }

        return new MyUserDetails(user.get());
    }
}