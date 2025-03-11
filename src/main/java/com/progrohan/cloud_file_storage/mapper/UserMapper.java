package com.progrohan.cloud_file_storage.mapper;

import com.progrohan.cloud_file_storage.dto.UserRequestDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequestDTO dto);

    UserResponseDTO toDto(UserEntity entity);

    UserResponseDTO toResponseDto(UserRequestDTO requestDTO);

}
