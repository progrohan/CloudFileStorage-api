package com.progrohan.cloud_file_storage.docs.resource;

import com.progrohan.cloud_file_storage.dto.ErrorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(    ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Delete a resource",
        description = "Deletes a file or directory at the specified path",
        parameters = {
                @Parameter(name = "path",
                        description = "Full path to the resource (URL-encoded). Directories must end with '/'",
                        required = true,
                        example = "/folder1/file.txt")
        },
        responses = {
                @ApiResponse(
                        responseCode = "204",
                        description = "Resource successfully deleted"
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Invalid or missing path",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "message": "Invalid path format"
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
                                                    "message": "Unauthorized"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "404",
                        description = "Resource not found",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "message": "Resource not found"
                                                }
                                                """
                                )
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Unknown server error",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ErrorResponseDTO.class),
                                examples = @ExampleObject(
                                        value = """
                                                {
                                                    "message": "Unexpected server error"
                                                }
                                                """
                                )
                        )
                )
        }
)
public @interface DeleteResourceDocs {
}
