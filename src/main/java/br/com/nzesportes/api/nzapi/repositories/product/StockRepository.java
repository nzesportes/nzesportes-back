package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

    @Query(value =
            "BEGIN; " +
            "UPDATE stock " +
            "SET quantity = quantity + :quantity " +
            "WHERE id = :id " +
            "RETURNING *; " +
            "commit; ", nativeQuery = true)
    Stock updateQuantity(UUID id, Integer quantity);

    @Query(value = "commit;", nativeQuery = true)
    Stock commit();


    @Query(value = "UPDATE stock " +
                    "SET status = NOT status " +
                    "WHERE id = :id " +
                    "RETURNING *; ", nativeQuery = true)
    Stock updateStatus(UUID id);
}
