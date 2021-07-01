package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.Stock;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDetailSaveTO {
    private String color;
    private String size;
    private BigDecimal price;
    private Gender gender;
    private Boolean status;
    private UUID productId;
    private Integer quantity;
    private List<Stock> stock;
}
