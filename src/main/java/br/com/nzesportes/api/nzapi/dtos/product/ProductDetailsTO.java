package br.com.nzesportes.api.nzapi.dtos.product;

import br.com.nzesportes.api.nzapi.domains.product.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @JsonIgnore
    private String size;
    @JsonIgnore
    private Integer quantity;
    @JsonIgnore
    private Double discount;
    private UUID purchaseStockId;
    private LocalDateTime creationDate;
}
