package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    Coupon findAllById(UUID id);

    @Query(value = "SELECT * FROM coupons c ORDER BY difference(c.code, ?1) DESC", nativeQuery = true)
    Page<Coupon> findByFilter(String name, Pageable pageable);

}
