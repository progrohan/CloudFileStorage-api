package com.progrohan.cloud_file_storage.repository;

import com.progrohan.cloud_file_storage.entity.StorageEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface StorageRepository extends JpaRepository<StorageEntity, Long> {

    @NotNull
    @Override
    Optional<StorageEntity> findById(@NotNull Long id);

    @NotNull
    @Override
    <S extends StorageEntity> S saveAndFlush(@NotNull S entity);

}
