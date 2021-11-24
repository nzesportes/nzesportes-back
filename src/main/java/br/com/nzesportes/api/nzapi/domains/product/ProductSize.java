package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "product_sizes")
public class ProductSize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String size;
    private String chest;
    private String height;
    private String length;
    private String sleeve;
    private String shoulder;
    private String width;
    private String indicated_height;
    private String indicated_weight;
    private String image;
    private String name;
}
