package br.com.nzesportes.api.nzapi.dtos.enums;

public enum EmailContentEnum {
    COMPRA_CONFIRMADA("Olá! Recebemos seu pedido. Somente quando recebermos a confirmação, seguiremos com o envio das suas compras. O prazo de entrega passa a ser contado somente após a confirmação do pagamento."),
    COMPRA_APROVADA("O pagamento do seu pedido foi aprovado. Agradecemos sua preferência por nossa loja!"),
    COMPRA_REJEITADA("Ops! O pagamento do seu pedido foi rejeitado. Entre em contato sua credora!"),
    COMPRA_CANCELADA("O pagamento do seu pedido foi cacelado. Entre em contato sua credora!"),
}
