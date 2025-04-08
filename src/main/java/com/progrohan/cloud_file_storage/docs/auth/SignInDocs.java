package com.progrohan.cloud_file_storage.docs.auth;

import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import com.progrohan.cloud_file_storage.dto.UserRequestDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "User login",
        description = "Authenticates the user and starts a session",
        requestBody = @RequestBody(
                description = "User login request",
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = UserRequestDTO.class),
                        examples = {
                                @ExampleObject(name = "Valid request",
                                        value = """
                                                {
                                                    "username": "user123",
                                                    "password": "Password@123"
                                                }
                                                """),
                                @ExampleObject(name = "Invalid request - short username(min length 5)",
                                        value = """
                                                {
                                                    "username": "us",
                                                    "password": "Password@123"
                                                }
                                                """),
                                @ExampleObject(name = "Invalid request - short password(min length 5)",
                                        value = """
                                                {
                                                    "username": "user123",
                                                    "password": "123"
                                                }
                                                """),
                                @ExampleObject(
                                        name = "Invalid Request - Invalid password format",
                                        value = """
                                                {
                                                    "username": "user123",
                                                    "password": "password123"
                                                }
                                                """)
                        }
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful login",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = UserResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                  "username": "Yaroslav"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Validation error",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "message": "Request body is empty or invalid. Please provide required fields.",
                                                    "errors": {
                                                        "password": "Password must be between 8 and 32 characters.",
                                                        "username": "Username must be between 3 and 32 characters."
                                                    }
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "401",
                        description = "Invalid username or password",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "message": "Invalid username or password"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Unknown error",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "message": "Any problems with database"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface SignInDocs {
}
