package br.com.nzesportes.api.nzapi.dtos.purchase;

import br.com.nzesportes.api.nzapi.dtos.product.StockTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PurchaseItemsTO {
    private UUID id;
    private Integer quantity;
    private BigDecimal cost;
    private StockTO item;
}
