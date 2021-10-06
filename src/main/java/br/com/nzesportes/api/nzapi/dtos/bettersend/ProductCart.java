package br.com.nzesportes.api.nzapi.dtos.bettersend;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductCart {
    public String name;
    public int quantity;
    public BigDecimal unitary_value;

    public ProductCart(String name, int quantity, BigDecimal unitary_value) {
        this.name = name;
        this.quantity = quantity;
        this.unitary_value = unitary_value;
    }
}
