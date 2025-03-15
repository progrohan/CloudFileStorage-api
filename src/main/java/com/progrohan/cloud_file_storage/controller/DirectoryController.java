package com.progrohan.cloud_file_storage.controller;

import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.service.DirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/directory")
@RequiredArgsConstructor
public class DirectoryController {

    private final DirectoryService directoryService;

    @PostMapping()
    public ResponseEntity<ResourceResponseDTO> createEmptyDirectory(@RequestParam String path,
                                                                    @AuthenticationPrincipal UserDetails userDetails){
        ResourceResponseDTO resource = directoryService.createEmptyDirectory(userDetails.getUsername(), path);


        return ResponseEntity.created(URI.create("/api/directory")).body(resource);
    }

    @GetMapping()
    public ResponseEntity<List<ResourceResponseDTO>> getDirectoriesResources(@RequestParam String path, @AuthenticationPrincipal UserDetails userDetails){

        List<ResourceResponseDTO> directoriesResources = directoryService.getDirectoriesResources(userDetails.getUsername(), path);

        return ResponseEntity.ok(directoriesResources);
    }


}
