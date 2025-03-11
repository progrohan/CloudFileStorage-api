package com.progrohan.cloud_file_storage.handler;

import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import com.progrohan.cloud_file_storage.exception.AuthException;
import com.progrohan.cloud_file_storage.exception.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExistsException(UserExistException e) {

        ErrorResponseDTO conflictError = new ErrorResponseDTO(e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictError);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadCredentialsException(AuthException e) {

        ErrorResponseDTO authError = new ErrorResponseDTO(e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authError);
    }
}
