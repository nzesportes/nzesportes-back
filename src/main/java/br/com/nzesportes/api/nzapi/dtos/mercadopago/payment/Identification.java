package br.com.nzesportes.api.nzapi.dtos.mercadopago.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Identification {
    public String type;
    public int number;
}