package com.progrohan.cloud_file_storage.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(UserStorageId.class)
@Table(name = "user_storage")
@Entity
public class UserStorageEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Id
    @ManyToOne
    @JoinColumn(name = "storage_id", nullable = false)
    private StorageEntity storage;

    private Boolean owning;

    private Boolean primacy;


}
