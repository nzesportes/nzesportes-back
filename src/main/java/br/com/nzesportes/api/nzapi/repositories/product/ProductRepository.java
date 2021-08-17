package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.domains.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByModel(String model);

    boolean existsByBrandId(UUID brandId);

//    Page<Product> findByCategoryId(UUID categoryId, Pageable page);

    Page<Product> findByModelContaining(String model, Pageable of);
//
//    @Query("SELECT p FROM Product p where p.model LIKE CONCAT('%', :model ,'%') AND :category MEMBER OF p.category")
//    Page<Product> findByModelContainingAndCategoryContaining(String model, Category category, Pageable of);
//
//    @Query("SELECT p FROM Product p where p.model LIKE CONCAT('%', :model ,'%') AND :category MEMBER OF p.category AND p.status = :status")
//    Page<Product> findByModelContainingAndCategoryContainingAndStatus(String model, Category category, Boolean status, Pageable of);

    Page<Product> findByStatus(Boolean status, Pageable pageRequest);
}
