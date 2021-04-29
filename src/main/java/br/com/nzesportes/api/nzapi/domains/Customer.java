package br.com.nzesportes.api.nzapi.domains;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public String name;
    public String lastName;
    public UUID userId;
    public String instagram;
    public String phone;
    public LocalDate birthDate;
    public String cpf;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public Customer(UUID id, String name, String lastName, UUID userId, String instagram, String phone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.userId = userId;
        this.instagram = instagram;
        this.phone = phone;
    }

    public Customer() {
    }
}
