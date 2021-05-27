package br.com.nzesportes.api.nzapi.domains.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "products_details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String color;
    private String size;
    private BigDecimal price;
    private String niche;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Sale sale;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonIgnore
    private Product product;
    private Integer quantity;

    @PrePersist
    private void prePersist() {
        this.status = false;
    }
}
