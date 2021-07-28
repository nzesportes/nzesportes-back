package br.com.nzesportes.api.nzapi.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "recovery_request")
public class RecoveryRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private RecoveryType type;
    private Boolean status;
}
