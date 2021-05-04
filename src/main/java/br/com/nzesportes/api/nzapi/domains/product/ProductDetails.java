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
    private String size;
    private BigDecimal price;
    @ManyToMany
    private List<Category> category;
    private String niche;
    @ManyToOne
    private Brand brand;
    @ManyToOne
    private Sale sale;
    @Enumerated(EnumType.STRING)
    private Gender gender;
}
