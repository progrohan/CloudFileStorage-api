package com.progrohan.cloud_file_storage.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStorageId implements Serializable {


    private Long user;
    private Long storage;


}
