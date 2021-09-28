package br.com.nzesportes.api.nzapi.dtos.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class UpdateStockTO {
    private UUID id;
    private Integer quantityToAdd;

}
