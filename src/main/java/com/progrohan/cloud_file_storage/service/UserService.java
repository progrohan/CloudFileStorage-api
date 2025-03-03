package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.UserRegistrationDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.entity.User;
import com.progrohan.cloud_file_storage.mapper.UserMapper;
import com.progrohan.cloud_file_storage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;
    private final UserRepository userRepository;

    UserResponseDTO save(UserRegistrationDTO userDTO){

        User savedUser = userRepository.save(mapper.toEntity(userDTO));

        return mapper.toDto(savedUser);

    }




}
