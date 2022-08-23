package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    Boolean existsByName(String name);

    @Query(value = "SELECT * FROM brands b ORDER BY difference(b.name, ?1) DESC", nativeQuery = true)
    Page<Brand> findByFilter(String name, Pageable pageable);

    @Query(value = "SELECT * FROM brands b WHERE b.status = true ORDER BY difference(b.name, ?1) DESC", nativeQuery = true)
    Page<Brand> findByFilterAndStatusTrue(String name, Pageable pageable);

    @Query(value = "SELECT * FROM brands b WHERE b.status = true AND b.on_menu = true ORDER BY b.name", nativeQuery = true)
    List<Brand> findOnMenu();

    Optional<Brand> findByName(String name);
}
