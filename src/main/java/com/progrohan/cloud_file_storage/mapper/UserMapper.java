package com.progrohan.cloud_file_storage.mapper;

import com.progrohan.cloud_file_storage.dto.UserRequestDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "storageAccesses", ignore = true)
    UserEntity toEntity(UserRequestDTO dto);

    UserResponseDTO toDto(UserEntity entity);

    UserResponseDTO toResponseDto(UserRequestDTO requestDTO);

}
