package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.product.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ProductDetailsTO {
    private UUID id;
    private String color;
    private BigDecimal price;
    private String description;
    private Sale sale;
    private Boolean status;
    private String images;
    private UUID productId;
    private List<Stock> stock;
    private List<SubCategory> subCategories;
    private ProductTO product;
}
