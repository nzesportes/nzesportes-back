package br.com.nzesportes.api.nzapi.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
}