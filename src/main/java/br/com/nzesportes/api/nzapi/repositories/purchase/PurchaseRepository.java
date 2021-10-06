package br.com.nzesportes.api.nzapi.repositories.purchase;

import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    Optional<Purchase> findByIdAndCustomerId(UUID id, UUID customerId);
}
