package com.progrohan.cloud_file_storage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

    private String name;

    private String password;

    private String passwordConfirmation;

}
