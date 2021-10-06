package br.com.nzesportes.api.nzapi.dtos.customer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RecoveryTO {
    private UUID id;
}
