package com.progrohan.cloud_file_storage.repository;


import com.progrohan.cloud_file_storage.exception.ResourceNotFoundException;
import com.progrohan.cloud_file_storage.exception.StorageException;
import com.progrohan.cloud_file_storage.service.UserService;
import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.GetObjectArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.Result;
import io.minio.StatObjectArgs;
import io.minio.errors.MinioException;
import io.minio.messages.Item;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public Iterable<Result<Item>> findResources(String prefix){

        return minioClient.listObjects(ListObjectsArgs.builder()
                        .bucket(rootBucket)
                        .prefix(prefix)
                        .recursive(true)
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


    public void deleteResource(String path){
        try{
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(rootBucket)
                            .object(path)
                            .build());
        }catch(Exception e){
            throw new StorageException("Problem with deleting resource");
        }
    }

    public void checkIfResourceExists(String path){
        if (!path.endsWith("/")){
            try{
                minioClient.statObject(StatObjectArgs.builder()
                        .bucket(rootBucket)
                        .object(path)
                        .build());
            }catch (MinioException e){
                throw new ResourceNotFoundException("Resource not found!");
            }catch (Exception e){
                throw new StorageException("Problem with getting resource!");
            }
        }
    }

    public void renameFile(String path, String newPath){
        try{
            minioClient.copyObject(CopyObjectArgs.builder()
                    .source(CopySource.builder()
                            .bucket(rootBucket)
                            .object(path)
                            .build())
                    .bucket(rootBucket)
                    .object(newPath)
                    .build());
        }catch (Exception e){
            throw new StorageException("Problem with renaming file!");
        }
        deleteResource(path);
    }

    public void renameFolder(String path, String newPath){

        try{
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(rootBucket).prefix(path).recursive(true).build()
            );

            for (Result<Item> result : results) {
                Item item = result.get();
                String oldObjectName = item.objectName();
                String newObjectName = oldObjectName.replaceFirst(path, newPath);

                renameFile(oldObjectName, newObjectName);

            }
        }catch (Exception e){
            throw new StorageException("Problem with renaming folder!");
        }

    }

    public void uploadFile(String path, MultipartFile file){
        try (var inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(rootBucket)
                            .object(path)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e){
            throw  new StorageException("Problem with uploading file!");
        }

    }

    public InputStreamResource downloadFile(String path){

        try{
            var inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(rootBucket)
                            .object(path)
                            .build());

            return new InputStreamResource(inputStream);

        }catch (Exception e){
            throw new StorageException("Problem with downloading file!");
        }
    }

    public InputStreamResource downloadFolder(String path) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            try (ZipOutputStream zipOut = new ZipOutputStream(byteArrayOutputStream)) {
                var results = minioClient.listObjects(
                        ListObjectsArgs.builder()
                                .bucket(rootBucket)
                                .prefix(path)
                                .recursive(true)
                                .build()
                );

                for (Result<Item> result : results) {
                    Item item = result.get();
                    if (item.isDir()) continue;

                    String objectName = item.objectName();
                    String relativeName = objectName.substring(path.length());

                    try (InputStream fileStream = minioClient.getObject(
                            GetObjectArgs.builder()
                                    .bucket(rootBucket)
                                    .object(objectName)
                                    .build()
                    )) {
                        zipOut.putNextEntry(new ZipEntry(relativeName));
                        fileStream.transferTo(zipOut);
                        zipOut.closeEntry();
                    }
                }

                zipOut.finish();
            }

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            return new InputStreamResource(byteArrayInputStream);

        } catch (Exception e) {
            throw new StorageException("Problem with downloading folder!");
        }
    }
}
