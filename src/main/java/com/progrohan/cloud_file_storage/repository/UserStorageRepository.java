package com.progrohan.cloud_file_storage.repository;


import com.progrohan.cloud_file_storage.entity.UserStorageEntity;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface UserStorageRepository extends JpaRepository<UserStorageEntity, Long> {

    @NotNull
    @Override
    Optional<UserStorageEntity> findById(@NotNull Long id);

    @NotNull
    Optional<UserStorageEntity> findByUserIdAndStorageId(@NotNull  Long userId,Long storageId);

    @NotNull
    @Override
    <S extends UserStorageEntity> S saveAndFlush(@NotNull S entity);



}
