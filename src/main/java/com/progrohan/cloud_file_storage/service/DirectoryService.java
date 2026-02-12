package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.exception.ResourceAlreadyExistException;
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

    private final MinioStorageRepository minioStorageRepository;
    private final ResourceService resourceService;
    private final StorageService storageService;

    public ResourceResponseDTO createEmptyDirectory(Long storageId, String path){

        String finalPath = storageService.getStorage(storageId).getName() + path;

        if(Boolean.TRUE.equals(resourceService.checkResource(storageId, path))) throw new ResourceAlreadyExistException("Папка с таким именем уже существует в текущей директории");

        minioStorageRepository.createEmptyDirectory(finalPath);

        return resourceService.getResource(storageId, path);

    }

    public List<ResourceResponseDTO> getDirectoriesResources(Long storageId, String path){

        List<ResourceResponseDTO> resources = new ArrayList<>();

        String finalPath = storageService.getStorage(storageId).getName().replace("/", "") + path;

        minioStorageRepository.checkIfResourceExists(finalPath);

        try {
            Iterable<Result<Item>> results = minioStorageRepository.getDirectoriesResources(finalPath);

            for (Result<Item> result : results) {
                Item item = result.get();
                String resourcePath = item.objectName();

                if (Objects.equals(resourcePath, finalPath )) continue;

                int firstSlashIndex = resourcePath.indexOf('/');
                resourcePath = resourcePath.substring(firstSlashIndex + 1);

                if(Boolean.FALSE.equals(resourceService.checkResource(storageId, resourcePath))) continue;
                resources.add(resourceService.getResource(storageId, resourcePath));
            }
            return resources;
        }catch (Exception e){
            throw new StorageException("Problem with getting directories resources!");
        }

    }


}
