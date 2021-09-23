package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.SubCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

    boolean existsByNameAndGender(String name, Gender gender);

    @Query(value = "SELECT * FROM sub_categories sc WHERE (:gender = 'null' or sc.gender = :gender) ORDER BY difference(sc.name, :name) DESC /*#{#pageable}*/", nativeQuery = true)
    Page<SubCategory> findAllFilter(String name, String gender, Pageable pageable);

    @Query(value = "SELECT * FROM sub_categories sc WHERE (:gender = 'null' or sc.gender = :gender) AND sc.status = :status ORDER BY difference(sc.name, :name) DESC /*#{#pageable}*/", nativeQuery = true)
    Page<SubCategory> findAllFilterAndStatus(String name, String gender, Boolean status, Pageable pageable);


    @Query(value = "SELECT sc FROM SubCategory sc WHERE sc.category = :category")
    List<SubCategory> findByCategory(Category category);
}
