package com.progrohan.cloud_file_storage.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceResponseDTO {

    private String path;

    private String name;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private long size;

    private String type;

}
