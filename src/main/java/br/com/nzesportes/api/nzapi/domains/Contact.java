package br.com.nzesportes.api.nzapi.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String instagram;
    private String message;
    private Boolean read;

    @PrePersist
    private void prePersist() {
        this.read = false;
    }
}
