package com.progrohan.cloud_file_storage.controller;

import com.progrohan.cloud_file_storage.dto.ResponseMessageDTO;
import com.progrohan.cloud_file_storage.dto.UserRegistrationDTO;
import com.progrohan.cloud_file_storage.dto.UserRequestDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> signUp(@RequestBody UserRegistrationDTO userRegistrationDTO){

        UserResponseDTO user = authService.createUser(userRegistrationDTO);

        return ResponseEntity
                .created(URI.create("api/user/me"))
                .body(user);

    }

    public ResponseEntity<UserResponseDTO> signIn(@RequestBody UserRequestDTO userRequestDTO){


    }

}
