package br.com.nzesportes.api.nzapi.dtos.mercadopago.payment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentsResponse {
    public Paging paging;
    public List<Payment> results;
}
