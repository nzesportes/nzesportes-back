package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductDetailRepository extends JpaRepository<ProductDetails, UUID> {
    @Query("SELECT pd FROM ProductDetails pd, Product p, Stock s " +
            "WHERE pd.productId = p.id " +
            "AND s.productDetail.id = pd.id " +
            "AND (p.brand = :brand OR :brand IS NULL) " +
            "AND (s.size = :productSize OR :productSize IS NULL) " +
            "AND (:category MEMBER OF p.category OR :category IS NULL) " +
            "AND (pd.gender = :gender or :gender IS NULL) " +
            "AND (pd.color = :color or :color IS NULL) ")
    Page<ProductDetails> findByFilter(@Param("gender") Gender gender,
                                      @Param("category") Category category,
                                      @Param("productSize") String productSize,
                                      @Param("color") String color,
                                      @Param("brand") Brand brand,
                                      Pageable pageable);

    @Query("SELECT pd FROM ProductDetails pd, Product p, Stock s " +
            "WHERE pd.productId = p.id " +
            "AND s.productDetail.id = pd.id " +
            "AND (p.brand = :brand OR :brand IS NULL) " +
            "AND (:productSize  = '' OR s.size = :productSize ) " +
            "AND (pd.gender = :gender or :gender IS NULL) " +
            "AND (pd.color = :color or :color = '') ")
    Page<ProductDetails> findByFilter(@Param("gender") Gender gender,
                                      String productSize,
                                      @Param("color") String color,
                                      @Param("brand") Brand brand,
                                      Pageable pageable);

    @Query("SELECT pd FROM ProductDetails pd, Product p, Stock s " +
            "WHERE pd.productId = p.id " +
            "AND s.productDetail.id = pd.id " +
            "AND (p.brand = :brand OR :brand IS NULL) " +
            "AND (s.size = :productSize OR :productSize IS NULL) " +
            "AND (:category MEMBER OF p.category OR :category IS NULL) " +
            "AND (pd.gender = :gender or :gender IS NULL) " +
            "AND (pd.color = :color or :color IS NULL) " +
            "ORDER BY pd.price ASC")
    Page<ProductDetails> findByFilterASC(@Param("gender") Gender gender,
                                      @Param("category") Category category,
                                      @Param("productSize") String productSize,
                                      @Param("color") String color,
                                      @Param("brand") Brand brand,
                                      Pageable pageable);

    @Query("SELECT pd FROM ProductDetails pd, Product p, Stock s " +
            "WHERE pd.productId = p.id " +
            "AND s.productDetail.id = pd.id " +
            "AND (:brand IS NULL OR p.brand = :brand) " +
            "AND (s.size = :productSize OR :productSize IS NULL) " +
            "AND (pd.gender = :gender or :gender IS NULL) " +
            "AND (pd.color = :color or :color IS NULL) " +
            "ORDER BY pd.price ASC")
    Page<ProductDetails> findByFilterASC(@Param("gender") Gender gender,
                                         @Param("productSize") String productSize,
                                         @Param("color") String color,
                                         @Param("brand") Brand brand,
                                         Pageable pageable);

    @Query("SELECT pd FROM ProductDetails pd, Product p, Stock s " +
            "WHERE pd.productId = p.id " +
            "AND s.productDetail.id = pd.id " +
            "AND (p.brand = :brand OR :brand IS NULL) " +
            "AND (s.size = :productSize OR :productSize IS NULL) " +
            "AND (:category MEMBER OF p.category OR :category IS NULL) " +
            "AND (pd.gender = :gender or :gender IS NULL) " +
            "AND (pd.color = :color or :color IS NULL) " +
            "ORDER BY pd.price DESC")
    Page<ProductDetails> findByFilterDESC(@Param("gender") Gender gender,
                                         @Param("category") Category category,
                                         @Param("productSize") String productSize,
                                         @Param("color") String color,
                                         @Param("brand") Brand brand,
                                         Pageable pageable);

//    @Query("SELECT pd FROM ProductDetails pd WHERE (pd.gender = :gender or :gender IS NULL)")
//    Page<ProductDetails> findByFilter(@Param("gender") Gender gender,
//                                      Pageable pageable);
}
