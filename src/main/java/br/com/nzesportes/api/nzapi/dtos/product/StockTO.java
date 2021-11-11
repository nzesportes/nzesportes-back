package br.com.nzesportes.api.nzapi.dtos.product;


import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class StockTO {
    private UUID id;
    private ProductDetailsTO productDetail;
}
