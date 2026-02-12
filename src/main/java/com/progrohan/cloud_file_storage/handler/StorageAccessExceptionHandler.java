package com.progrohan.cloud_file_storage.handler;


import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import com.progrohan.cloud_file_storage.exception.StorageAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StorageAccessExceptionHandler {

    @ExceptionHandler(StorageAccessException.class)
    public ResponseEntity<ErrorResponseDTO> handleStorageAccessException(StorageAccessException e){

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponseDTO(e.getMessage()));

    }

}
