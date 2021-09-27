package br.com.nzesportes.api.nzapi.domains.purchase;

public enum PurchaseStatus {
    PENDING_PAYMENT("PENDING_PAYMENT"),
    PAYMENT_REPROVED("PAYMENT_REPROVED"),
    PAYMENT_DONE("PAYMENT_DONE"),
    CANCELED("CANCELED");

    private final String text;

    PurchaseStatus(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
