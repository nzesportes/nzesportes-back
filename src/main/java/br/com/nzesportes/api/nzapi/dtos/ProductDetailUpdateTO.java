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
public class ProductDetailUpdateTO {
    private UUID id;
    private String color;
    private String size;
    private String description;
    private BigDecimal price;
    private Gender gender;
    private Boolean status;
    private Integer quantity;
    private UUID productId;
    private List<Stock> stockToAdd;
    private List<UUID> stockToRemove;
}
