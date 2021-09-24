package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.domains.product.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Boolean existsByName(String name);

    @Query(value = "SELECT * FROM categories c ORDER BY difference(c.name, :name) /*#{#of}*/ DESC", nativeQuery = true)
    Page<Category> findByName(@Param("name") String name, Pageable of);


    @Query(value = "SELECT * FROM categories c WHERE c.status = :status ORDER BY difference(c.name, :name) DESC /*#{#of}*/", nativeQuery = true)
    Page<Category> findByFilterAndStatus(@Param("status") Boolean status, @Param("name") String name, Pageable of);

    @Query(value = "SELECT c FROM Category c, SubCategory sc WHERE sc.category = c AND sc.gender = :gender")
    List<Category> findCategoriesBySubCategoryGender(@Param("gender") Gender gender);

    Optional<Category> findByNameContaining(String name);

}
