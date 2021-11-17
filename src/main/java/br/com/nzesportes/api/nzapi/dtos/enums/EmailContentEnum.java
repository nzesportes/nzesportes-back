package br.com.nzesportes.api.nzapi.dtos.enums;

public enum EmailContentEnum {
    COMPRA_CONFIRMADA("Olá! Recebemos seu pedido. Somente quando recebermos a confirmação, seguiremos com o envio das suas compras. O prazo de entrega passa a ser contado somente após a confirmação do pagamento."),
    COMPRA_APROVADA("O pagamento do seu pedido foi aprovado. Agradecemos sua preferência por nossa loja!"),
    COMPRA_REJEITADA("Ops! O pagamento do seu pedido não foi aprovado. Entre em contato sua credora!"),
    COMPRA_CANCELADA("O pagamento do seu pedido foi cancelado por algum motivo. Entre em contato sua credora!"),
    COMPRA_ERRO_GENERICO("Ops! ocorreu um problema durante o pagamento, sentimos muito pelo ocorrido! Entre em contato para mais informações."),
    COMPRA_AVALIAR("Olá! Espero que você tenha gostado dos produtos da sua compra, seu feedback é muito importante. Gostaria de fazer uma avaliação do produto que você mais gostou? :) ");


    private final String text;

    EmailContentEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
