package com.progrohan.cloud_file_storage.docs.user;


import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import com.progrohan.cloud_file_storage.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Get user information",
        description = "Returns information about the authenticated user",
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successful response with user information",
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
                        responseCode = "401",
                        description = "User is not authenticated",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "message": "User is not authenticated"
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
                                                    "message": "Unknown error"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface UserDocs {
}
