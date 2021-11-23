package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

import lombok.ToString;

@ToString
public enum OrderPaymentStatus {
    paid("paid"),
    payment_in_process("payment_in_process"),
    partially_paid("partially_paid"),
    expired("expired");

    private final String text;

    OrderPaymentStatus(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
