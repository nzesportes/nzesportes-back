package br.com.nzesportes.api.nzapi.dtos.product;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductTO {
    public UUID id;
    private String model;
    private Brand brand;
    private Boolean status;
}
