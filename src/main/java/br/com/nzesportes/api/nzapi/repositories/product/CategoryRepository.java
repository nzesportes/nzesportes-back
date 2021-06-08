package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Boolean existsByName(String name);
    Page<Category> findAllByStatusAndTypeContainingAndNameContaining(Boolean status, String type, String name, PageRequest page);
}
