package br.com.nzesportes.api.nzapi.repositories.melhorenvio;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import br.com.nzesportes.api.nzapi.domains.product.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SizeRepository extends JpaRepository<Size, UUID> {

    @Query(value = "SELECT * FROM sizes s ORDER BY difference(s.type, ?1) DESC", nativeQuery = true)
    Page<Size> findByFilter(String type, Pageable pageable);
}
