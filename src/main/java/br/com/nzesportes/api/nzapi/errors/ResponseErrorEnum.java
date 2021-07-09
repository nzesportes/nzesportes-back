package br.com.nzesportes.api.nzapi.errors;

public enum ResponseErrorEnum {
    //ABSTRACT RESPONSE
    NOT_AUTH("NOT_AUTH - Não autorizado"),
    NOT_FOUND("NOT_FOUND - Não encontrado"),
    COMPLETED("COMPLETED - ação já realizada"),
    //Authentication
    AUTH001("AUTH001 - Usuário já registrado"),
    //PROFILE
    PRO001("PRO001 - Perfil perfil não encontrado"),
    PRO002("PRO002 - Perfil desse usuário já cadastrado ou token inválido"),
    PRO003("PRO003 - Perfil desse usuário não encontrado"),
    PDT001("PDT001 - Detalhes não encontrado"),
    //CATEGORY
    CAT001("CAT001 - Categoria com esse nome já existe"),
    CAT002("CAT002 - Categoria não encontrada"),
    //BRAND
    BRD001("BRD001 - Marca com esse nome já existe"),
    BRD002("BRD002 - Existem uma ou mais produtos com essa marca"),
    BRD003("BRD003 - Marca não encontrada"),
    PRD001("PRD001 - Produto não encontrado"),
    PRD002("PRD002 - Produto com esse modelo já existente"),
    ADR001("ADR001 - Endereço não encontrado"),
    ADR002("ADR002 - Você não pode fazer isso"),
    STK001("STK001 - Stock não encontrado"),
    USR001("USR001 - Usuário não encontrado"),
    USR002("USR001 - Você não pode fazer isso");


    private final String text;

    ResponseErrorEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
