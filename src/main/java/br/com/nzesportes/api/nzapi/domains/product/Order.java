package br.com.nzesportes.api.nzapi.domains.product;

public enum Order {
//    RELEVANT("RELEVANT"),
//    EXPENSIVE("EXPENSIVE"),
    DESC("DESC"),
    ASC("ASC");

    private final String text;

    Order(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
