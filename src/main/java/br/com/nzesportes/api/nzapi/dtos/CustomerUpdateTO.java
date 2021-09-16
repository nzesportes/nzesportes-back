package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerUpdateTO {
    private String name;
    private String lastName;
    private String instagram;
    private String phone;
    private LocalDate birthDate;
    private Gender gender;
}
