package br.com.nzesportes.api.nzapi.dtos.bettersend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class From {
    public String name;
    public String phone;
    public String email;
    public String document;
    public String company_document;
    public String state_register;
    public String address;
    public String complement;
    public String number;
    public String district;
    public String city;
    public String country_id;
    public String postal_code;
    public String note;

    public From(String name, String phone, String email, String document, String company_document, String state_register, String address, String complement, String number, String district, String city, String country_id, String postal_code, String note) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.document = document;
        this.company_document = company_document;
        this.state_register = state_register;
        this.address = address;
        this.complement = complement;
        this.number = number;
        this.district = district;
        this.city = city;
        this.country_id = country_id;
        this.postal_code = postal_code;
        this.note = note;
    }
}
