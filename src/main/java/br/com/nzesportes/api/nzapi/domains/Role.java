package br.com.nzesportes.api.nzapi.domains;

public enum Role {
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    USER("USER");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
