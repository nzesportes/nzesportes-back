package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderPage {
    private List<OrderTO> elements;
    private int next_offset;
    private int total;
}
