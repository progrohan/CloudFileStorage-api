package com.progrohan.cloud_file_storage.handler;

import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import com.progrohan.cloud_file_storage.exception.ResourceAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceAlreadyExistExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceAlreadyExistException e){

        ErrorResponseDTO response = new ErrorResponseDTO(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
