package br.com.nzesportes.api.nzapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductPaymentTO {
    private UUID stockId;
    private Integer quantity;
}
