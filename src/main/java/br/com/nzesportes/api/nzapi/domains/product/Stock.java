package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @JoinColumn(name = "product_detail_id")
    private UUID productDetailId;
    private String size;
    private Integer quantity;
}
