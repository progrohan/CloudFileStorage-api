package com.progrohan.cloud_file_storage.handler;

import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import com.progrohan.cloud_file_storage.exception.StorageException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StorageExceptionHandler {

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<ErrorResponseDTO> handleStorageException(StorageException e){
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO(e.getMessage()));
    }

}
