package com.progrohan.cloud_file_storage.mapper;


import com.progrohan.cloud_file_storage.dto.StorageResponseDTO;
import com.progrohan.cloud_file_storage.entity.StorageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StorageMapper{

    StorageResponseDTO toDTO(StorageEntity entity);



}
