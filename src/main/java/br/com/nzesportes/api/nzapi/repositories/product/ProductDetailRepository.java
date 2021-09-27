package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductDetailRepository extends JpaRepository<ProductDetails, UUID> {

    @Query("SELECT DISTINCT (pd) FROM ProductDetails pd, Product p " +
            "INNER JOIN pd.subCategories subCategory " +
            "INNER JOIN pd.stock stock " +
            "WHERE (:gender IS NULL OR (subCategory.gender = 'BOTH' OR subCategory.gender = :gender)) " +
            "AND (:subcategory IS NULL OR subCategory.name = :subcategory) " +
            "AND (:category IS NULL OR subCategory.category.name = :category) " +
            "AND (:productSize IS NULL OR stock.size = :productSize) " +
            "AND pd.productId = p.id " +
            "AND (p IN :nameSearch) " +
            "AND (:brand IS NULL OR p.brand.name = :brand) " +
            "AND (:color IS NULL OR pd.color = :color) " +
            "AND p.status = true AND pd.status = true")
    Page<ProductDetails> findByFilter(List<Product> nameSearch, Gender gender, String category, String subcategory, String productSize, String brand, String color, Pageable pageable);

    @Query("SELECT DISTINCT (pd) FROM ProductDetails pd, Product p " +
            "INNER JOIN pd.subCategories subCategory " +
            "INNER JOIN pd.stock stock " +
            "WHERE (:gender IS NULL OR (subCategory.gender = 'BOTH' OR subCategory.gender = :gender)) " +
            "AND (:subcategory IS NULL OR subCategory.name = :subcategory) " +
            "AND (:category IS NULL OR subCategory.category.name = :category) " +
            "AND (:productSize IS NULL OR stock.size = :productSize) " +
            "AND pd.productId = p.id " +
            "AND (:brand IS NULL OR p.brand.name = :brand) " +
            "AND (:color IS NULL OR pd.color = :color) " +
            "AND p.status = true AND pd.status = true")
    Page<ProductDetails> findByFilter(Gender gender, String category, String subcategory, String productSize, String brand, String color, Pageable pageable);

}
