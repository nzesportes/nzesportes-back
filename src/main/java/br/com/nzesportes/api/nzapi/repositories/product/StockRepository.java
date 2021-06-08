package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {
}
