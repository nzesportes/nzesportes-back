package br.com.nzesportes.api.nzapi.repositories.customer;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomersRepository extends JpaRepository<Customer, UUID> {
    Boolean existsByUserId(UUID userId);

    @Query(value = "select * from customers c ORDER BY difference(c.name || ' ' || c.last_name, :search)  DESC /*#{#pageable}*/;", nativeQuery = true)
    Page<Customer> search(@Param("search") String search, Pageable pageable);

    Optional<Customer> findByUserId(UUID uuid);
}
