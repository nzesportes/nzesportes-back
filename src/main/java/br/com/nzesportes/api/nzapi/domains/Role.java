package br.com.nzesportes.api.nzapi.domains;

public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MODERATOR("ROLE_MODERATOR"),
    ROLE_USER("ROLE_USER");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

}
