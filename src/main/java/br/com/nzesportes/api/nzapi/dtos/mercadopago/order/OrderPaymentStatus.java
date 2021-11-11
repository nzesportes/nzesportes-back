package br.com.nzesportes.api.nzapi.dtos.mercadopago.order;

public enum OrderPaymentStatus {
    paid("paid");

    private final String text;

    OrderPaymentStatus(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
