package com.progrohan.cloud_file_storage.repository;



import com.progrohan.cloud_file_storage.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @NotNull
    @Override
    Optional<User> findById(@NotNull Long id);

    User findByUsername(String username);

    @NotNull
    @Override
    <S extends User> S saveAndFlush(@NotNull S entity);



    
}
