package br.com.nzesportes.api.nzapi.repositories.customer;

import br.com.nzesportes.api.nzapi.domains.customer.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByCustomerId(UUID id);
}
