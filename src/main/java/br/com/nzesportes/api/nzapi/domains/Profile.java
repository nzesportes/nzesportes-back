package br.com.nzesportes.api.nzapi.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public String name;
    public String lastName;
    public UUID userId;
    public String instagram;
    public String phone;
    public String cpf;

    public Profile (UUID id, String name, String lastName, UUID userId, String instagram, String phone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.userId = userId;
        this.instagram = instagram;
        this.phone = phone;
    }

    public Profile() {
    }
}
