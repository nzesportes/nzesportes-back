package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByModel(String model);

    boolean existsByBrandId(UUID brandId);

    Page<Product> findAllByCategoryId(UUID categoryId, PageRequest page);
}
