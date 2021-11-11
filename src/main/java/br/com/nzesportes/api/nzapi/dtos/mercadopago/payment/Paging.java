package br.com.nzesportes.api.nzapi.dtos.mercadopago.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
    public int total;
    public int limit;
    public int offset;
}