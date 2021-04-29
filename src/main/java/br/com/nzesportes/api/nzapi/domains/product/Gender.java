package br.com.nzesportes.api.nzapi.domains.product;

public enum Gender {
    F("F"),
    M("M");

    private final String text;

    Gender(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
