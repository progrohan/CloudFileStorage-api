package com.progrohan.cloud_file_storage.handler;


import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import com.progrohan.cloud_file_storage.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserNotFoundExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(UserNotFoundException e){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(e.getMessage()));

    }

}
