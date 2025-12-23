package com.progrohan.cloud_file_storage.repository;

import com.progrohan.cloud_file_storage.entity.UserStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserStorageRepository extends JpaRepository<UserStorageEntity, Long> {

}
