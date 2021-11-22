package br.com.nzesportes.api.nzapi.repositories.purchase;

import br.com.nzesportes.api.nzapi.domains.purchase.MercadoPagoPaymentStatus;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    Optional<Purchase> findByIdAndCustomerId(UUID id, UUID customerId);

    List<Purchase> findByStatusNot(MercadoPagoPaymentStatus status);

    @Query(value = "SELECT p.* FROM purchases p, payment_requests pr WHERE p.payment_request_id = pr.id AND p.customer_id = :customerId ORDER BY pr.creation_date DESC", nativeQuery = true)
    Page<Purchase> findAllByCustomerId(UUID customerId, Pageable pageable);

    Purchase findAllById(UUID id);

    @Query(value = "SELECT p.* FROM purchases p, payment_requests pr WHERE p.payment_request_id = pr.id ORDER BY pr.creation_date DESC", nativeQuery = true)
    Page<Purchase> findAllPurchase(Pageable pageable);
    List<Purchase> findByStatus(MercadoPagoPaymentStatus status);

    Page<Purchase> findByCode(BigInteger code, Pageable pageable);
}
