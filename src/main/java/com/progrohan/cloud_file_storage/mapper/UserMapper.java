package com.progrohan.cloud_file_storage.mapper;

import com.progrohan.cloud_file_storage.dto.UserRegistrationDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import com.progrohan.cloud_file_storage.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRegistrationDTO dto);

    UserResponseDTO toDto(UserEntity entity);

}
