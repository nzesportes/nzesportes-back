package br.com.nzesportes.api.nzapi.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@Table(name = "recovery_request")
public class RecoveryRequest {
    private UUID userId;
    private UUID id;
    private RecoveryType type;
    @Enumerated(EnumType.STRING)
    private Boolean status;
}
