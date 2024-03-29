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
    private UUID id;
    private String model;
    @ManyToOne
    private Brand brand;
    @OneToMany(mappedBy = "productId", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductDetails> productDetails;
    private Boolean status;
    @ManyToOne
    private Size size;
}
