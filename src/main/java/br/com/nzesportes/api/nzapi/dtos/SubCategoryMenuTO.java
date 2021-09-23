package br.com.nzesportes.api.nzapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SubCategoryMenuTO {
    private UUID id;
    private String name;
}
