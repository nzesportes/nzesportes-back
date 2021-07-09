package br.com.nzesportes.api.nzapi.domains;

public enum RecoveryType {
    FIRST_ACCESS("FIRST_ACCESS"),
    PASSWORD_RECOVERY("PASSWORD_RECOVERY");

    private final String text;

    RecoveryType(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    }
