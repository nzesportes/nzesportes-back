package br.com.nzesportes.api.nzapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SubCategoryMenuTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
}
