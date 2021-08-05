package br.com.nzesportes.api.nzapi.repositories;

import br.com.nzesportes.api.nzapi.domains.BetterSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BetterSendRepository extends JpaRepository<BetterSend, String> {
}
