package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Item {
    private String id;
    private String title;
    private String currency_id;
    private String picture_url;
    private String description;
    private String category_id;
    private Integer quantity;
    private BigDecimal unit_price;
}
