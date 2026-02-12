package com.progrohan.cloud_file_storage.service;

import com.progrohan.cloud_file_storage.dto.AccessDTO;
import com.progrohan.cloud_file_storage.dto.StorageResponseDTO;
import com.progrohan.cloud_file_storage.entity.StorageEntity;
import com.progrohan.cloud_file_storage.entity.UserEntity;
import com.progrohan.cloud_file_storage.entity.UserStorageEntity;
import com.progrohan.cloud_file_storage.exception.ResourceNotFoundException;
import com.progrohan.cloud_file_storage.exception.StorageAccessException;
import com.progrohan.cloud_file_storage.mapper.StorageMapper;
import com.progrohan.cloud_file_storage.repository.MinioStorageRepository;
import com.progrohan.cloud_file_storage.repository.StorageRepository;
import com.progrohan.cloud_file_storage.repository.UserStorageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class StorageService {

    private final StorageMapper storageMapper;

    private final UserService userService;

    private final MinioStorageRepository minioStorageRepository;
    private final StorageRepository storageRepository;
    private final UserStorageRepository userStorageRepository;

    public StorageResponseDTO createStorage(String userName, String name, Boolean primacy){

        name +=  "/";

        minioStorageRepository.createRootFolder(name);
        StorageEntity storage = new StorageEntity(name);

        storageRepository.saveAndFlush(storage);

        UserEntity user = userService.loadUserEntityByUsername(userName);

        UserStorageEntity userStorageEntity = UserStorageEntity
                .builder()
                .user(user)
                .storage(storage)
                .owning(Boolean.TRUE)
                .primacy(primacy)
                .build();
        storage.getUserAccesses().add(userStorageEntity);

        return storageMapper.toDTO(storage);
    }

    public StorageResponseDTO renameStorage(Long storageId, String newName){

        StorageEntity storage = storageRepository
                .findById(storageId)
                .orElseThrow(() -> new ResourceNotFoundException("Storage does not exist!"));

        storage.setName(newName);

        return storageMapper.toDTO(storage);
    }

    public void deleteStorage(Long storageID){

        storageRepository.deleteById(storageID);

    }

    public StorageResponseDTO getStorage(Long id){

        return storageRepository
                .findById(id)
                .map(storageMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Storage does not exist!"));

    }

    public List<StorageResponseDTO> getAvailableStorages(String name){

        UserEntity user = userService.loadUserEntityByUsername(name);
        List<StorageEntity> storages = storageRepository
                .findStoragesByUserId(user.getId());

        return storages.stream().map(storageMapper::toDTO).toList();

    }

    public StorageResponseDTO shareAccess(AccessDTO accessDTO){

        StorageEntity storage = storageRepository
                .findById(accessDTO.getStorageId())
                .orElseThrow(() -> new ResourceNotFoundException("Storage does not exist!"));

        UserEntity user = userService.loadUserEntityByUsername(accessDTO.getUserName());

        UserStorageEntity userStorage = UserStorageEntity
                .builder()
                .user(user)
                .storage(storage)
                .owning(Boolean.FALSE)
                .primacy(Boolean.FALSE)
                .build();

        storage.getUserAccesses().add(userStorage);

        return  storageMapper.toDTO(storage);
    }

    public void removeAccess(AccessDTO accessDTO){

        StorageEntity storage = storageRepository
                .findById(accessDTO.getStorageId())
                .orElseThrow(() -> new ResourceNotFoundException("Storage does not exist!"));

        UserEntity user = userService.loadUserEntityByUsername(accessDTO.getUserName());

        UserStorageEntity userStorageEntity = userStorageRepository
                .findByUserIdAndStorageId( user.getId(), storage.getId())
                .orElseThrow(() -> new StorageAccessException("Access does not exists"));

        userStorageRepository.delete(userStorageEntity);

    }

    public void checkAccess(String userName, Long storageId){

        UserEntity user = userService.loadUserEntityByUsername(userName);
        getStorage(storageId);

        userStorageRepository
                .findByUserIdAndStorageId(user.getId(), storageId)
                .orElseThrow(() -> new StorageAccessException("NO ACCESS"));

    }

}

