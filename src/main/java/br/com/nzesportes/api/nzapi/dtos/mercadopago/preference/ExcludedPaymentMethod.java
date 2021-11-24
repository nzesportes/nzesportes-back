package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcludedPaymentMethod {
    private String id;
}
