package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class ExcludedPaymentMethod {
    private String id;
}
