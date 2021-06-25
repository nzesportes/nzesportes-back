package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "products_details")
public class ProductDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String color;
    private BigDecimal price;
    private String description;
    @ManyToOne
    private Sale sale;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean status;
    @JoinColumn
    private UUID productId;
    private Boolean onStock;
    @OneToMany(mappedBy = "productDetail", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Stock> stock;
    @PrePersist
    private void prePersist() {
        this.status = false;
        if(this.stock != null && this.stock.size() > 0)
            this.stock.forEach(s -> s.setProductDetail(this));
    }
}
