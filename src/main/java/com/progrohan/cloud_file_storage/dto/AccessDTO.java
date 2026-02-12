package com.progrohan.cloud_file_storage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessDTO {

    @NotNull(message = "validation.storage.id.required")
    @Positive(message = "validation.storage.id.positive")
    private Long storageId;

    @NotBlank(message = "validation.user.name.required")
    @Size(min = 3,max = 16, message = "validation.user.name.length")
    private String userName;

}
