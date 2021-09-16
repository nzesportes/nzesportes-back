package br.com.nzesportes.api.nzapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentTO {
    private List<ProductPaymentTO> products;
    private Double shipment;
}
