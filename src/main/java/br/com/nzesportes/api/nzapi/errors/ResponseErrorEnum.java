package br.com.nzesportes.api.nzapi.errors;

public enum ResponseErrorEnum {
    //Authentication
    AUTH001("AUTH001 - Usuário já registrado"),
    //PROFILE
    PRO001("PRO001 - Perfil perfil não encontrado"),
    PRO002("PRO002 - Perfil desse usuário já cadastrado ou token inválido"),
    //CATEGORY
    CATO001("CATO001 - Categoria com esse nome já existe");


    private final String text;

    ResponseErrorEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
