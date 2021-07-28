package br.com.nzesportes.api.nzapi.domains.product;

public enum Order {
    RELEVANT("RELEVANT"),
    CHEAP("CHEAP"),
    EXPENSIVE("EXPENSIVE"),
    SALE("SALE");

    private final String text;

    Order(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
