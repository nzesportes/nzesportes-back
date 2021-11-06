package br.com.nzesportes.api.nzapi.dtos.purchase;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PaymentRequestTO {
    private UUID id;
    private LocalDateTime creationDate;
}
