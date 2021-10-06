package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaymentMethods {
    private List<ExcludedPaymentMethod> excluded_payment_methods;
    private List<ExcludedPaymentType> excluded_payment_types;
    private Integer installments;
}
