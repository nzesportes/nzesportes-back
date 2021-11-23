package br.com.nzesportes.api.nzapi.domains.product;

import lombok.ToString;

@ToString
public enum Gender {
    FEMALE("FEMALE"),
    MALE("MALE"),
    BOTH("BOTH");

    private final String text;

    Gender(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
