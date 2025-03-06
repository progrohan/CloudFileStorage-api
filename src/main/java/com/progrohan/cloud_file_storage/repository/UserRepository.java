package com.progrohan.cloud_file_storage.repository;



import com.progrohan.cloud_file_storage.entity.UserEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @NotNull
    @Override
    Optional<UserEntity> findById(@NotNull Long id);

    Optional<UserEntity> findByUsername(String username);

    @NotNull
    @Override
    <S extends UserEntity> S saveAndFlush(@NotNull S entity);



    
}
