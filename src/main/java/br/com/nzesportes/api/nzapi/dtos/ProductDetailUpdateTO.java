package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductDetailUpdateTO {
    private UUID id;
    private String color;
    private String size;
    private BigDecimal price;
    private String niche;
    private Gender gender;
    private Boolean status;
    private Integer quantity;
}
