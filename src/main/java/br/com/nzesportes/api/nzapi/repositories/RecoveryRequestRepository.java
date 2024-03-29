package br.com.nzesportes.api.nzapi.repositories;

import br.com.nzesportes.api.nzapi.domains.customer.RecoveryRequest;
import br.com.nzesportes.api.nzapi.domains.customer.RecoveryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecoveryRequestRepository extends JpaRepository<RecoveryRequest, UUID> {
    RecoveryRequest findByUserIdAndType(UUID id, RecoveryType type);
}
