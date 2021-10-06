package br.com.nzesportes.api.nzapi.dtos.bettersend;

import br.com.nzesportes.api.nzapi.domains.customer.Address;
import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class BetterSendCart {
    public int service;
    public int agency;
    public From from;
    public To to;
    public List<ProductCart> products;
    public List<Volume> volumes;
    public Options options;


    public BetterSendCart(){
        this.from = new From(
                "NZ_NAME",
                "NZ_FONE",
                "NZ_EMAIL",
                "",
                "NZ_CNPJ",
                "NZ_INSCRICAO",
                "NZ_ADDRESS",
                "",
                "NZ_NUMBER",
                "NZ_DISTRICT",
                "NZ_CITY",
                "BR",
                "NZ_CEP",
               ""
        );
        this.products = new ArrayList<>();
        this.volumes = new ArrayList<>();
    }


    public void setTo(Address from, Customer customer, String email) {
        this.to = new To(
                from.getAddressee(),
                from.getPhone(),
                email,
                customer.getCpf(),
                "",
                "",
                from.getStreet(),
                from.getComplement(),
                from.getNumber(),
                from.getDistrict(),
                from.getCity(),
                from.getState(),
                "BR",
                from.getCep(),
                ""
        );
    }
}
