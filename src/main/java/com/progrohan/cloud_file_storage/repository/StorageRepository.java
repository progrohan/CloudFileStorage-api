package com.progrohan.cloud_file_storage.repository;

import com.progrohan.cloud_file_storage.entity.StorageEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface StorageRepository extends JpaRepository<StorageEntity, Long> {

    @NotNull
    @Override
    Optional<StorageEntity> findById(@NotNull Long id);

    @Query("SELECT s FROM StorageEntity s JOIN s.userAccesses u WHERE u.user.id = :userID")
    List<StorageEntity> findStoragesByUserId(@Param("userID") Long id);

    @NotNull
    @Override
    <S extends StorageEntity> S saveAndFlush(@NotNull S entity);

}
