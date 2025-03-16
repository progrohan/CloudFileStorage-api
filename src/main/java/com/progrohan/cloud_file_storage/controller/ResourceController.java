package com.progrohan.cloud_file_storage.controller;

import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetMapping()
    public ResponseEntity<ResourceResponseDTO> getResource(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String path){

        ResourceResponseDTO resource = resourceService.getResource(userDetails.getUsername(), path);

        return ResponseEntity.ok(resource);

    }

    @GetMapping("/move")
    public ResponseEntity<ResourceResponseDTO> moveResource(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String from, @RequestParam String to){

        ResourceResponseDTO resource = resourceService.renameResource(userDetails.getUsername(), from, to);

        return ResponseEntity.ok(resource);

    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String path){

        resourceService.deleteResource(userDetails.getUsername(), path);

    }

}
