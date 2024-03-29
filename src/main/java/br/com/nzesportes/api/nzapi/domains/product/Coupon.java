package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String code;
    private BigDecimal discount;
    private Integer quantity;
    private Boolean status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer quantityLeft;
}
