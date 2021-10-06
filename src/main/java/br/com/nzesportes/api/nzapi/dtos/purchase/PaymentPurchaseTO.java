package br.com.nzesportes.api.nzapi.dtos.purchase;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PaymentPurchaseTO {
    public UUID purchaseId;
    public String paymentUrl;
}
