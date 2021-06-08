package br.com.nzesportes.api.nzapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateStockTO {
    private UUID id;
    private UUID productDetailId;
    //How much to add or remove from stock;
    private Integer quantityToAdd;

}
