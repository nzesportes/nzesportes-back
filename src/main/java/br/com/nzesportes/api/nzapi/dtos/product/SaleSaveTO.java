package br.com.nzesportes.api.nzapi.dtos.product;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SaleSaveTO {
    private Double percentage;
    private Integer quantity;
    private UUID productDetailId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
