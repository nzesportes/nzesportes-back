package br.com.nzesportes.api.nzapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductUpdateTO {
    private UUID id;
    private String model;
    private Boolean status;
}
