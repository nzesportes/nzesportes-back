package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductDetailRepository extends JpaRepository<ProductDetails, UUID> {
//    @Query("SELECT pd FROM ProductDetails pd WHERE (pd.gender = :gender or :gender IS NULL)")
//    Page<ProductDetails> findByFilter(@Param("gender") Gender gender,
//                                      @Param("category") String category,
//                                      @Param("productSize") String productSize,
//                                      @Param("color") String color,
//                                      @Param("brand") String brand,
//                                      Pageable pageable);
    @Query("SELECT pd FROM ProductDetails pd WHERE (pd.gender = :gender or :gender IS NULL)")
    Page<ProductDetails> findByFilter(@Param("gender") Gender gender,
                                      Pageable pageable);
}
