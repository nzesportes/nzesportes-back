package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectorTO {
    private int id;
    private String email;
    private String nickname;
}
