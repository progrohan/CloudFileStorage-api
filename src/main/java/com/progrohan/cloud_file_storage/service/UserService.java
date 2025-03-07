package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.UserRegistrationDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.entity.UserEntity;
import com.progrohan.cloud_file_storage.exception.UserNotFoundException;
import com.progrohan.cloud_file_storage.mapper.UserMapper;
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

    private final UserMapper mapper;
    private final UserRepository userRepository;

    UserResponseDTO save(UserRegistrationDTO userDTO){

        UserEntity savedUser = userRepository.save(mapper.toEntity(userDTO));

        return mapper.toDto(savedUser);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.emptyList()
                )).orElseThrow(() -> new UsernameNotFoundException("User with username" + username));
    }
}
