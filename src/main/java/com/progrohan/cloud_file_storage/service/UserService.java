package com.progrohan.cloud_file_storage.service;


import com.progrohan.cloud_file_storage.entity.UserEntity;
import com.progrohan.cloud_file_storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                )).orElseThrow(() -> new UsernameNotFoundException("User with username" + username + "does not exist!"));
    }

    public Long getUserId(String name){
        UserEntity user = userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("User with username" + name + "does not exist!"));

        return user.getId();
    }
}
