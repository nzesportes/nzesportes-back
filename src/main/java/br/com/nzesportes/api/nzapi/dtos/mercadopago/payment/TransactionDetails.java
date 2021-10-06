package br.com.nzesportes.api.nzapi.dtos.mercadopago.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetails {
    public int net_received_amount;
    public int total_paid_amount;
    public int overpaid_amount;
    public int installment_amount;
}
