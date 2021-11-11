package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemTO {
    private String id;
    private String category_id;
    private String currency_id;
    private String description;
    private String picture_url;
    private String picture_id;
    private String title;
    private int quantity;
    private BigDecimal unit_price;
}
