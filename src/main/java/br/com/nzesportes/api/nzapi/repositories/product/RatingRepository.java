package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UUID> {
    Optional<Rating> findByPurchaseId(UUID purchaseId);

    Page<Rating> findByCustomerIdOrderByCreationDateDesc(UUID customerId, Pageable pageable);

    Page<Rating> findByProductId(UUID id, Pageable pageable);


    Page<Rating> findByPurchaseId(UUID id, Pageable pageable);

}
