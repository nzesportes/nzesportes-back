package br.com.nzesportes.api.nzapi.errors;

public enum ResponseErrorEnum {
    //ABSTRACT RESPONSE
    NOT_AUTH("NOT_AUTH - Não autorizado"),
    NOT_FOUND("NOT_FOUND - Não encontrado"),
    COMPLETED("COMPLETED - Ação já realizada"),
    CONFLICTED_OPERATION("CONFLICTED_OPERATION - Esta operação não pode ser realizada"),
    EXPIRED("EXPIRED - Expirado"),

    //Authentication
    AUTH001("AUTH001 - Usuário já registrado"),

    //PROFILE
    PRO001("PRO002 - Perfil desse usuário já cadastrado ou token inválido"),

    //CATEGORY
    CAT001("CAT001 - Categoria com esse nome já existe"),

    //BRAND
    BRD001("BRD001 - Marca com esse nome já existe"),
    BRD002("BRD002 - Existem uma ou mais produtos com essa marca"),

    //PRODUCT
    PRD002("PRD002 - Produto com esse modelo já existente"),

    //STOCK
    STK001("STK002 - Erro ao atualizar quantidade de estoque"),

    SCT001("SCT001 - Já existe sub categoria com este nome"),
    SCT003("SCT003 - Sub categoria já com produtos atrelados"),
    PAY001("PAY001 - Sem estoque"),
    RAT002("RAT002 - produto não faz parte da compra"),

    //SALE
    SAL001("SAL001 - Existe uma promoção entre essas datas ou data incorreta"),
    SAL002("SAL002 - Não foi possível deleter promoção que já iniciou"),

    //COUPON
    CPN001("CPN001 - Existe um cupom entre essas datas ou data incorreta");

    private final String text;

    ResponseErrorEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
