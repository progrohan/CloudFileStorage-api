package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.ResourceResponseDTO;
import com.progrohan.cloud_file_storage.exception.StorageException;
import com.progrohan.cloud_file_storage.repository.MinioStorageRepository;
import io.minio.Result;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ResourceService {

    private final MinioStorageRepository storageRepository;

    public ResourceResponseDTO getResource(String userName,String reqPath){

        boolean isDirectory = false;

        String path = storageRepository.getUserRootFolderByName(userName) + reqPath;

        storageRepository.checkIfResourceExists(path);

        ResourceResponseDTO resource = new ResourceResponseDTO();

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
            isDirectory = true;
            resource.setType("DIRECTORY");
        }else{
            resource.setType("FILE");
            resource.setSize(storageRepository.getResourcesSize(path));
        }

        int lastSlashIndex = path.lastIndexOf('/');
        int firstSlashIndex = path.indexOf('/');
        String resourcePath = path.substring(firstSlashIndex + 1, lastSlashIndex + 1);
        String name = path.substring(lastSlashIndex + 1);

        name += isDirectory ? "/" : "";

        resource.setPath(resourcePath);
        resource.setName(name);


        return resource;
    }


    public List<ResourceResponseDTO> findResources(String userName, String query){

        List<ResourceResponseDTO> resources = new ArrayList<>();
        String prefix = storageRepository.getUserRootFolderByName(userName);


        try {
            Iterable<Result<Item>> results = storageRepository.findResources(prefix);

            for (Result<Item> result : results) {

                Item item = result.get();
                String resourcePath = item.objectName();

                if (resourcePath.contains(query)) {
                    int firstSlashIndex = resourcePath.indexOf('/');

                    resources.add(getResource(userName, resourcePath.substring(firstSlashIndex + 1)));
                }
            }
            return resources;
        }catch (Exception e){
            throw new StorageException("Problem with finding resources!");
        }

    }

    public void deleteResource(String userName, String reqPath){

        String path = storageRepository.getUserRootFolderByName(userName) + reqPath;

        storageRepository.checkIfResourceExists(path);

        storageRepository.deleteResource(path);

    }

    public ResourceResponseDTO renameResource(String userName, String reqPath, String newPath){

        String oldPath = storageRepository.getUserRootFolderByName(userName) + reqPath;
        String path = storageRepository.getUserRootFolderByName(userName) + newPath;

        storageRepository.checkIfResourceExists(oldPath);

        if (path.endsWith("/")) {
            storageRepository.renameFolder(oldPath, path);
        }else{
            storageRepository.renameFile(oldPath, path);
        }

        return getResource(userName, newPath);

    }

    public List<ResourceResponseDTO> uploadFile(String userName, List<MultipartFile> files, String reqPath){

        List<ResourceResponseDTO> resources = new ArrayList<>();

        for(MultipartFile file: files){
            String path = storageRepository.getUserRootFolderByName(userName) + reqPath + file.getOriginalFilename();

            storageRepository.uploadFile(path, file);

            resources.add(getResource(userName, reqPath + file.getOriginalFilename()));
        }

       return resources;
    }

    public InputStreamResource downloadResource(String userName, String reqPath){
        String path = storageRepository.getUserRootFolderByName(userName) + reqPath;

        InputStreamResource inputStreamResource;

        if(path.endsWith("/")){
           inputStreamResource = storageRepository.downloadFolder(path);
        }
        else{
            inputStreamResource = storageRepository.downloadFile(path);
        }

        return inputStreamResource;

    }


}
