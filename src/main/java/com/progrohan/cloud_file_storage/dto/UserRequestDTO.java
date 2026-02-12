package com.progrohan.cloud_file_storage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "validation.user.name.required")
    @Size(min = 3, max = 16, message = "validation.user.name.length")
    private String username;

    @NotBlank(message = "validation.user.password.required")
    @Size(min = 8, message = "validation.user.password.length")
    private String password;

}
