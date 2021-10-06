package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "productDetailId")
    private List<Sale> sales;
    private Boolean status;
    private String images;
    @JoinColumn
    private UUID productId;
    @OneToMany(mappedBy = "productDetail", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Stock> stock;
    private LocalDateTime creationDate;

    @ManyToMany
    @JoinTable(
            name = "product_details_sub_categories",
            joinColumns = @JoinColumn(name = "product_details_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_category_id")
    )
    private List<SubCategory> subCategories;
    @PrePersist
    private void prePersist() {
        this.creationDate = LocalDateTime.now();
        if(this.stock != null && this.stock.size() > 0)
            this.stock.forEach(s -> s.setProductDetail(this));
    }
}
