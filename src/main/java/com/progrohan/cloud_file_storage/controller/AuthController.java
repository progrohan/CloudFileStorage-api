package com.progrohan.cloud_file_storage.controller;

import com.progrohan.cloud_file_storage.dto.UserRequestDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.service.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody UserRequestDTO userRequestDTO){

        UserResponseDTO user = authService.createUser(userRequestDTO);

        return ResponseEntity
                .created(URI.create("api/user/me"))
                .body(user);

    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDTO> signIn(@RequestBody UserRequestDTO userRequestDTO, HttpSession session){

        UserResponseDTO user = authService.loginUser(userRequestDTO, session);

        return ResponseEntity.ok(user);

    }

}
