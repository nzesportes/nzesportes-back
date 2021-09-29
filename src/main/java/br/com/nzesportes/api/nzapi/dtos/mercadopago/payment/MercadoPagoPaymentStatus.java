package br.com.nzesportes.api.nzapi.dtos.mercadopago.payment;

public enum MercadoPagoPaymentStatus {
    pending("pending"), // The user has not yet completed the payment process.
    approved("approved"), // The payment has been approved and accredited.
    authorized("authorized"), // The payment has been authorized but not captured yet.
    in_process("in_process"), // Payment is being reviewed.
    in_mediation("in_mediation"), // Users have initiated a dispute.
    rejected("rejected"), // Payment was rejected. The user may retry payment.
    cancelled("cancelled"), // Payment was cancelled by one of the parties or because time for payment has expired
    refunded("refunded"), // Payment was refunded to the user.
    charged_back("charged_back"); // Was made a chargeback in the buyer’s credit card.

    private final String text;

    MercadoPagoPaymentStatus(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
