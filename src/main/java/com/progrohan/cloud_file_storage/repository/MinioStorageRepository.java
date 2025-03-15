package com.progrohan.cloud_file_storage.repository;


import com.progrohan.cloud_file_storage.exception.StorageException;
import com.progrohan.cloud_file_storage.service.UserService;
import io.minio.BucketExistsArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
@RequiredArgsConstructor
public class MinioStorageRepository {

    private final MinioClient minioClient;
    private final UserService userService;

    @Value("${minio.rootBucket}")
    private String rootBucket;

    @PostConstruct
    public void createBucketIfNotExists() {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(rootBucket).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(rootBucket).build());
            }

        }catch(Exception e){
            throw new StorageException("Problem with creating bucket!");
        }
    }

    public void createUsersRootFolder(String name){
        try{
            String folderName = getUserRootFolderByName(name);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(rootBucket)
                            .object(folderName)
                            .stream(new ByteArrayInputStream(new byte[0]), 0, -1)
                            .contentType("application/x-directory")
                            .build()
            );
        }catch (Exception e){
            throw new StorageException("Problem with creating users root folder!");
        }
    }

    public void createEmptyDirectory(String path){
        try{

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(rootBucket)
                            .object(path)
                            .stream(new ByteArrayInputStream(new byte[0]), 0, -1)
                            .contentType("application/x-directory")
                            .build()
            );
        }catch (Exception e){
            throw new StorageException("Problem with creating folder!");
        }

    }

    public Iterable<Result<Item>> getDirectoriesResources(String path){

        return minioClient.listObjects(ListObjectsArgs.builder()
                .bucket(rootBucket)
                .prefix(path)
                .recursive(false)
                .build());

    }

    public String getUserRootFolderByName(String name){
        return String.format("user-%d-files/", userService.getUserId(name));
    }

    public long getResourcesSize(String path){
        try{
            return minioClient.statObject(StatObjectArgs.builder()
                    .bucket(rootBucket)
                    .object(path)
                    .build()).size();
        }catch (Exception e){
            throw new StorageException("Problem with getting resources size!");
        }

    }



}
