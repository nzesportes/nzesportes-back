package br.com.nzesportes.api.nzapi.repositories.melhorenvio;

import br.com.nzesportes.api.nzapi.domains.product.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SizeRepository extends JpaRepository<Size, UUID> {
}
