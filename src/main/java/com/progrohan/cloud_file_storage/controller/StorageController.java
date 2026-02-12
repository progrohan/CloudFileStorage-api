package com.progrohan.cloud_file_storage.controller;


import com.progrohan.cloud_file_storage.dto.AccessDTO;
import com.progrohan.cloud_file_storage.dto.StorageCreateDTO;
import com.progrohan.cloud_file_storage.dto.StorageRequestDTO;
import com.progrohan.cloud_file_storage.dto.StorageResponseDTO;
import com.progrohan.cloud_file_storage.service.StorageService;
import com.progrohan.cloud_file_storage.validation.ValidId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/storage")
@Validated
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("/{id}")
    public ResponseEntity<StorageResponseDTO> getStorage(@AuthenticationPrincipal UserDetails userDetails,
                                                         @PathVariable
                                                         @ValidId
                                                         Long id) {

        storageService.checkAccess(userDetails.getUsername(), id);

        StorageResponseDTO storage = storageService.getStorage(id);

        return ResponseEntity.ok(storage);
    }

    @GetMapping("/available")
    public ResponseEntity<List<StorageResponseDTO>> getAvailableStorages(@AuthenticationPrincipal UserDetails userDetails) {

        List<StorageResponseDTO> storages = storageService.getAvailableStorages(userDetails.getUsername());

        return ResponseEntity.ok(storages);

    }

    @PostMapping("/new-storage")
    public ResponseEntity<StorageResponseDTO> createNewStorage(@AuthenticationPrincipal UserDetails userDetails, @RequestBody
    StorageCreateDTO storageDTO) {

        StorageResponseDTO storage = storageService.createStorage(userDetails.getUsername(), storageDTO.getName(), Boolean.FALSE);

        URI location = UriComponentsBuilder
                .fromPath("api/storage/{id}")
                .buildAndExpand(storage.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(storage);

    }

    @PatchMapping("/rename")
    public ResponseEntity<StorageResponseDTO> renameStorage(@AuthenticationPrincipal UserDetails userDetails,
                                                            @Valid
                                                            @RequestBody
                                                            StorageRequestDTO storageRequestDTO) {

        storageService.checkAccess(userDetails.getUsername(), storageRequestDTO.getId());

        StorageResponseDTO storage = storageService.renameStorage(storageRequestDTO.getId(), storageRequestDTO.getName());

        return ResponseEntity.ok(storage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStorage(@AuthenticationPrincipal UserDetails userDetails,
                                              @PathVariable
                                              @ValidId
                                              Long id) {

        storageService.checkAccess(userDetails.getUsername(), id);

        storageService.deleteStorage(id);

        return ResponseEntity.noContent().build();

    }

    @PostMapping("/share")
    public ResponseEntity<StorageResponseDTO> shareAccess(@AuthenticationPrincipal UserDetails userDetails,
                                                          @Valid @RequestBody AccessDTO accessDTO) {

        storageService.checkAccess(userDetails.getUsername(), accessDTO.getStorageId());

        StorageResponseDTO responseDTO = storageService.shareAccess(accessDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/remove_access")
    public ResponseEntity<Void> removeAccess(@RequestBody AccessDTO accessDTO) {

        storageService.checkAccess(accessDTO.getUserName(), accessDTO.getStorageId());

        storageService.removeAccess(accessDTO);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/check_access")
    public ResponseEntity<Void> checkAccess(@AuthenticationPrincipal UserDetails user, @RequestParam Long storageId) {

        storageService.checkAccess(user.getUsername(), storageId);

        return ResponseEntity.ok().build();
    }


}
