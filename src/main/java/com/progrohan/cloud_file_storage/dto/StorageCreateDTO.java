package com.progrohan.cloud_file_storage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageCreateDTO {

    @NotBlank(message = "Название хранилища не должно быть пустым")
    @Size(min = 3, max = 20, message = "Название хранилища должно быть от 3 до 20 символов")
    private String name;

}
