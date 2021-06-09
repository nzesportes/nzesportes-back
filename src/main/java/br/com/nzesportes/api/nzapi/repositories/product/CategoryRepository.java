package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Boolean existsByName(String name);

    @Query(value = "SELECT * FROM categories c WHERE (:type = 'null' OR c.type LIKE %:type%) ORDER BY difference(c.name, :name) /*#{#of}*/ DESC", nativeQuery = true)
    Page<Category> findByFilter(@Param("type") String type, @Param("name") String name, Pageable of);


    @Query(value = "SELECT * FROM categories c WHERE c.status = :status AND (:type = 'null' OR c.type LIKE %:type%) ORDER BY difference(c.name, :name) DESC /*#{#of}*/", nativeQuery = true)
    Page<Category> findByFilterAndStatus(@Param("status") Boolean status, @Param("type") String type, @Param("name") String name, Pageable of);
}
