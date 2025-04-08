package com.progrohan.cloud_file_storage.controller;

import com.progrohan.cloud_file_storage.docs.resource.DeleteResourceDocs;
import com.progrohan.cloud_file_storage.docs.resource.DownloadResourceDocs;
import com.progrohan.cloud_file_storage.docs.resource.GetResourceDocs;
import com.progrohan.cloud_file_storage.docs.resource.MoveResourceDocs;
import com.progrohan.cloud_file_storage.docs.resource.SearchResourceDocs;
import com.progrohan.cloud_file_storage.docs.resource.UploadResourceDocs;
import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.service.ResourceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Tag(name = "Resources", description = "Endpoints for resources manipulations")
@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @GetResourceDocs
    @GetMapping()
    public ResponseEntity<ResourceResponseDTO> getResource(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String path){

        ResourceResponseDTO resource = resourceService.getResource(userDetails.getUsername(), path);

        return ResponseEntity.ok(resource);

    }

    @DownloadResourceDocs
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadResource(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String path){

        InputStreamResource inputStreamResource = resourceService.downloadResource(userDetails.getUsername(), path);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(inputStreamResource);

    }


    @UploadResourceDocs
    @PostMapping()
    public ResponseEntity<List<ResourceResponseDTO>> uploadFile(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String path, @RequestParam List<MultipartFile> object){


        List<ResourceResponseDTO> resourceResponseDTOs = resourceService.uploadFile(userDetails.getUsername(), object, path);

        return ResponseEntity.created(URI
                .create("api/resource/search"))
                .body(resourceResponseDTOs);

    }

    @MoveResourceDocs
    @GetMapping("/move")
    public ResponseEntity<ResourceResponseDTO> moveResource(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String from, @RequestParam String to){

        ResourceResponseDTO resource = resourceService.renameResource(userDetails.getUsername(), from, to);

        return ResponseEntity.ok(resource);

    }

    @SearchResourceDocs
    @GetMapping("/search")
    public ResponseEntity<List<ResourceResponseDTO>> search(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String query){

        List<ResourceResponseDTO> resources = resourceService.findResources(userDetails.getUsername(), query);

        return ResponseEntity.ok(resources);

    }

    @DeleteResourceDocs
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String path){

        resourceService.deleteResource(userDetails.getUsername(), path);

    }

}
