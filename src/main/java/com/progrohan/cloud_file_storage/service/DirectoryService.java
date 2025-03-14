package com.progrohan.cloud_file_storage.service;


import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.repository.MinioStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DirectoryService {

    private final UserService userService;
    private final MinioStorageRepository storageRepository;
    private final ResourceService resourceService;

    public ResourceResponseDTO createEmptyDirectory(String userName, String path){

        String finalPath = storageRepository.getUserRootFolderByName(userName) + path + "/";

        storageRepository.createEmptyDirectory(userName, finalPath);

        return resourceService.getResource(finalPath);

    }


}
