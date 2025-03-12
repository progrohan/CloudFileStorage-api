package com.progrohan.cloud_file_storage.service;


import com.progrohan.cloud_file_storage.dto.UserRequestDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.exception.AuthException;
import com.progrohan.cloud_file_storage.exception.UserExistException;
import com.progrohan.cloud_file_storage.mapper.UserMapper;
import com.progrohan.cloud_file_storage.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
@Transactional
class AuthServiceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    private HttpSession session;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        session = mock(HttpSession.class);
    }

    @Test
    void testCreateUser_Success() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("testuser");
        userRequestDTO.setPassword("password123");

        UserResponseDTO responseDTO = authService.createUser(userRequestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getUsername()).isEqualTo("testuser");
        assertThat(userRepository.findByUsername("testuser")).isPresent();
    }

    @Test
    void testCreateUser_UserAlreadyExists() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("existinguser");
        userRequestDTO.setPassword("password123");

        authService.createUser(userRequestDTO);

        assertThatThrownBy(() -> authService.createUser(userRequestDTO))
                .isInstanceOf(UserExistException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void testLoginUser_Success() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("loginuser");
        userRequestDTO.setPassword("securepass");

        authService.createUser(userRequestDTO);

        UserRequestDTO loginRequest = new UserRequestDTO();
        loginRequest.setUsername("loginuser");
        loginRequest.setPassword("securepass");

        UserResponseDTO responseDTO = authService.loginUser(loginRequest, session);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getUsername()).isEqualTo("loginuser");
        verify(session).setAttribute(eq("SPRING_SECURITY_CONTEXT"), any());
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        UserRequestDTO loginRequest = new UserRequestDTO();
        loginRequest.setUsername("unknownuser");
        loginRequest.setPassword("wrongpass");

        assertThatThrownBy(() -> authService.loginUser(loginRequest, session))
                .isInstanceOf(AuthException.class)
                .hasMessageContaining("Credentials incorrect!");
    }
}