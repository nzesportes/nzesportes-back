package br.com.nzesportes.api.nzapi.domains.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    @JsonIgnore
    private ProductDetails productDetail;
    private Boolean onStock;
    private String size;
    private Integer quantity;
}
