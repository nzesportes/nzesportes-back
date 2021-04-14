package br.com.nzesportes.api.nzapi.domains;

public enum Role {
    ADMIN("ROLE_ADMIN"),
    MODERATOR("ROLE_MODERATOR"),
    USER("ROLE_USER");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
