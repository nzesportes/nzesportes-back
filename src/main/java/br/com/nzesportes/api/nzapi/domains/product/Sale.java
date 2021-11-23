package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
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
    @JoinColumn
    private UUID productDetailId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer quantityLeft;
    private Boolean status;
}
