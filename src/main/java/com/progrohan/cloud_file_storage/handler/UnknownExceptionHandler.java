package com.progrohan.cloud_file_storage.handler;

import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnknownExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException() {
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Unknown error"));
    }

}
