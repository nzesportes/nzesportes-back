package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value =
            "BEGIN; " +
            "UPDATE stock " +
            "SET quantity = quantity + :quantity " +
            "WHERE id = :id ; " +
            "commit; ",nativeQuery = true)
    void updateQuantity(UUID id, Integer quantity);

    @Query(value = "commit;", nativeQuery = true)
    void commit();


    @Query(value = "UPDATE stock " +
                    "SET status = NOT status " +
                    "WHERE id = :id " +
                    "RETURNING *; ", nativeQuery = true)
    Stock updateStatus(UUID id);
}
