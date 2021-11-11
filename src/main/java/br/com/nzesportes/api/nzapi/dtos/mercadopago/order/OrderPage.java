package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderPage {
    private List<OrderTO> elements;
    private int next_offset;
    private int total;
}
