package br.com.nzesportes.api.nzapi.errors;

public enum ResponseError {
    //Authentication
    AUTH001("AUTH001 - Usuário já registrado");

    private final String text;

    ResponseError(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
