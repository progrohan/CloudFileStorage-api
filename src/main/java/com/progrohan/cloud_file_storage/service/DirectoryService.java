package com.progrohan.cloud_file_storage.service;


import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.exception.StorageException;
import com.progrohan.cloud_file_storage.repository.MinioStorageRepository;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DirectoryService {

    private final MinioStorageRepository storageRepository;
    private final ResourceService resourceService;

    public ResourceResponseDTO createEmptyDirectory(String userName, String path){

        String finalPath = storageRepository.getUserRootFolderByName(userName) + path + "/";

        storageRepository.createEmptyDirectory(finalPath);

        return resourceService.getResource(userName, path);

    }

    public List<ResourceResponseDTO> getDirectoriesResources(String userName, String path){

        List<ResourceResponseDTO> resources = new ArrayList<>();

        String finalPath = storageRepository.getUserRootFolderByName(userName) + path + "/";

        try {
            Iterable<Result<Item>> results = storageRepository.getDirectoriesResources(finalPath);

            for (Result<Item> result : results) {
                Item item = result.get();
                String resourcePath = item.objectName();

                if (Objects.equals(resourcePath, finalPath )) continue;

                int firstSlashIndex = resourcePath.indexOf('/');
                int lastSlashIndex = resourcePath.lastIndexOf('/');

                resources.add(resourceService.getResource(userName, resourcePath.substring(firstSlashIndex + 1, lastSlashIndex)));
            }

            return resources;
        }catch (Exception e){
            throw new StorageException("Problem with getting directories resources!");
        }

    }


}
