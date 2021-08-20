package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByModel(String model);

    boolean existsByBrandId(UUID brandId);

    Page<Product> findByModelContaining(String model, Pageable of);

    Page<Product> findByModelContainingAndStatus(String model,Boolean status, Pageable of);

    @Query(value = "SELECT * FROM products p WHERE difference(p.model, :name) > 2 ORDER BY difference(p.model, :name) DESC", nativeQuery = true)
    List<Product> findByProductName(String name);
}
