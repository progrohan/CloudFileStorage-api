package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.UserRegistrationDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.entity.UserEntity;
import com.progrohan.cloud_file_storage.mapper.UserMapper;
import com.progrohan.cloud_file_storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO createUser(UserRegistrationDTO userRegistrationDTO){

        userRegistrationDTO.setPassword(passwordEncoder
                .encode(userRegistrationDTO.getPassword()));

        UserEntity userEntity = userRepository.saveAndFlush(userMapper.toEntity(userRegistrationDTO));

        return userMapper.toDto(userEntity);

    }

}
