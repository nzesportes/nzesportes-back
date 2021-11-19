package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    Page<Sale> findByProductDetailIdOrderByEndDateDesc(UUID productId, Pageable pageable);

    List<Sale> findByStartDateEquals(LocalDate date);

    List<Sale> findByEndDateEquals(LocalDate date);

    Sale findAllById(UUID id);
}
