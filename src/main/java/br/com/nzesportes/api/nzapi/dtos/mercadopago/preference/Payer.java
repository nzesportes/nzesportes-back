package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Payer {
    private String name;
    private String surname;
    private String email;
    private Phone phone;
    private Identification identification;
    private Address address;
}
