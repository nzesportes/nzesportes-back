package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    private String description;
    private String model;
    @ManyToMany
    @JoinTable(
            name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> category;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductDetails> productDetails;
    private Boolean status;

    @PrePersist
    private void prePersist() {
        this.status = false;
    }
}
