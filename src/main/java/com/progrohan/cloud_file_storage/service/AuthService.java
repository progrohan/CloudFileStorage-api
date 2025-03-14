package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.UserRequestDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.entity.UserEntity;
import com.progrohan.cloud_file_storage.exception.AuthException;
import com.progrohan.cloud_file_storage.exception.StorageException;
import com.progrohan.cloud_file_storage.exception.UserExistException;
import com.progrohan.cloud_file_storage.mapper.UserMapper;
import com.progrohan.cloud_file_storage.repository.MinioStorageRepository;
import com.progrohan.cloud_file_storage.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final MinioStorageRepository storageService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        if (userRepository.findByUsername(userRequestDTO.getUsername()).isPresent())
            throw new UserExistException("User with username " + userRequestDTO.getUsername() + " already exists!");

        userRequestDTO.setPassword(passwordEncoder
                .encode(userRequestDTO.getPassword()));

        UserEntity userEntity = userRepository.saveAndFlush(userMapper.toEntity(userRequestDTO));

        try {
            storageService.createUsersRootFolder(userEntity.getUsername());
        } catch (StorageException e) {
            userRepository.delete(userEntity);
            throw e;
        }

        return userMapper.toDto(userEntity);

    }

    public UserResponseDTO loginUser(UserRequestDTO user, HttpSession session) {
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(auth);

            session.setAttribute("SPRING_SECURITY_CONTEXT", context);

            return userMapper.toResponseDto(user);
        } catch (AuthenticationException e) {
            throw new AuthException("Credentials incorrect!");
        }
    }

}
