package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackUrls {
    private String success;
    private String failure;
    private String pending;
}
