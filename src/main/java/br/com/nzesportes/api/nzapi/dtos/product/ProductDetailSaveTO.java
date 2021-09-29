package br.com.nzesportes.api.nzapi.dtos.product;

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
    private String description;
    private BigDecimal price;
    private Gender gender;
    private Boolean status;
    private UUID productId;
    private String images;
    private List<Stock> stock;
    private List<UUID> subCategoriesToAdd;
    private List<UUID> subCategoriesToRemove;
}
