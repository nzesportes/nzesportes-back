package br.com.nzesportes.api.nzapi.repositories.product;

import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProductDetailRepository extends JpaRepository<ProductDetails, UUID> {

    @Query(value = "SELECT DISTINCT(pd.id), pd.* FROM products_details pd , products p, stock s, brands b, categories c, product_categories pc " +
            "WHERE pd.product_id = p.id AND s.product_detail_id = pd.id AND b.id = p.brand_id " +
            "AND pc.category_id = c.id AND pc.product_id = p.id " +
            "AND (p.model LIKE ) " +
            "AND (:brand = 'null' or b.name = :brand) " +
            "AND (:category = 'null' or c.name = :category) " +
            "AND (:productSize = 'null' or s.size = :productSize) " +
            "AND (:color = 'null' or pd.color = :color) " +
            "AND (:gender = 'null' or pd.gender = :gender) " +
            "ORDER BY pd.price, difference(c.name, :name) " +
            "/*#{#pageable}*/", nativeQuery = true)
    Page<ProductDetails> filter(String name, String brand, String category, String productSize, String color, String gender, Pageable pageable);

//    Page<ProductDetails> filter(String name, String brand, String category, String productSize, String color, String s, Pageable pageable);
}
