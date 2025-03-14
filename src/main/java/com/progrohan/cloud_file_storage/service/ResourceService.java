package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.repository.MinioStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final MinioStorageRepository storageRepository;

    public ResourceResponseDTO getResource(String path){

        ResourceResponseDTO resource = new ResourceResponseDTO();

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
            resource.setType("DIRECTORY");
        }else{
            resource.setType("FILE");
            resource.setSize(storageRepository.getResourcesSize(path));
        }

        int lastSlashIndex = path.lastIndexOf('/');
        String resourcePath = path.substring(0, lastSlashIndex + 1);
        String name = path.substring(lastSlashIndex + 1);

        resource.setPath(resourcePath);
        resource.setName(name);

        return resource;
    }

}
