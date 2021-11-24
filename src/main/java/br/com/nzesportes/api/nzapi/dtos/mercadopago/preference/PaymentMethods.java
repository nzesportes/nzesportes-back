package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Data
public class PaymentMethods {
    private List<ExcludedPaymentMethod> excluded_payment_methods;
    private List<ExcludedPaymentType> excluded_payment_types;
    private Integer installments;
}
