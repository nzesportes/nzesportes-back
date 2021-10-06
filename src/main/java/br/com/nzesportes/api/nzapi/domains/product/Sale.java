package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Double percentage;
    private Integer quantity;
    private UUID productDetailId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer quantityLeft;
}
