package br.com.nzesportes.api.nzapi.dtos.customer;

import java.util.UUID;

public class AddressTO {
    private UUID id;
    private String addressee;
    private String cep;
    private String street;
    private String number;
    private String complement;
    private String reference;
    private String state;
    private String city;
    private String district;
    private String phone;
    private UUID customerId;
}
