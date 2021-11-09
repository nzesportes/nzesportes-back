package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String code;
    private Double discount;
    private Integer quantity;
    private Boolean status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer quantityLeft;
}
