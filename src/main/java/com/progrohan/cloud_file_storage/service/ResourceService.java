package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.repository.MinioStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final MinioStorageRepository storageRepository;

    public ResourceResponseDTO getResource(String userName,String reqPath){

        String path = storageRepository.getUserRootFolderByName(userName) + reqPath;

        storageRepository.checkIfResourceExists(path);

        ResourceResponseDTO resource = new ResourceResponseDTO();

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
            resource.setType("DIRECTORY");
        }else{
            resource.setType("FILE");
            resource.setSize(storageRepository.getResourcesSize(path));
        }

        int lastSlashIndex = path.lastIndexOf('/');
        int firstSlashIndex = path.indexOf('/');
        String resourcePath = path.substring(firstSlashIndex + 1, lastSlashIndex + 1);
        String name = path.substring(lastSlashIndex + 1);

        resource.setPath(resourcePath);
        resource.setName(name);

        return resource;
    }

    public void deleteResource(String userName, String reqPath){

        String path = storageRepository.getUserRootFolderByName(userName) + reqPath;

        storageRepository.checkIfResourceExists(path);

        storageRepository.deleteResource(path);

    }

    public ResourceResponseDTO renameResource(String userName, String reqPath, String newPath){

        String oldPath = storageRepository.getUserRootFolderByName(userName) + reqPath;
        String path = storageRepository.getUserRootFolderByName(userName) + newPath;

        storageRepository.checkIfResourceExists(reqPath);

        if (path.endsWith("/")) {
            storageRepository.renameFolder(oldPath, path);
        }else{
            storageRepository.renameFile(oldPath, path);
        }

        return getResource(userName, newPath);

    }

}
