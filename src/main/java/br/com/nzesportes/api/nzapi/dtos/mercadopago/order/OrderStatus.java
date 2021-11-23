package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import lombok.ToString;

@ToString
public enum OrderStatus {
    opened("opened"),
    closed("closed"),
    expired("expired");
    private final String text;

    OrderStatus(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
