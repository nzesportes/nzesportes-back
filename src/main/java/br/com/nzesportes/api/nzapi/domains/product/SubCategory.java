package br.com.nzesportes.api.nzapi.domains.product;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "sub_categories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean status;

    @ManyToMany
    @JoinTable(
            name = "categories_sub_categories",
            joinColumns = @JoinColumn(name = "sub_category_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @PrePersist
    private void prePersist() {
        this.status = false;
    }
}
