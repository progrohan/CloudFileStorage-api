package com.progrohan.cloud_file_storage.controller;

import com.progrohan.cloud_file_storage.docs.user.UserDocs;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "About me", description = "Endpoint for get user information")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @UserDocs
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getUser(@AuthenticationPrincipal UserDetails userDetails){

        UserResponseDTO userResponseDTO = new UserResponseDTO(userDetails.getUsername());

        return ResponseEntity.ok(userResponseDTO);
    }

}
