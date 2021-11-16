package br.com.nzesportes.api.nzapi.repositories.melhorenvio;

import br.com.nzesportes.api.nzapi.domains.BetterSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BetterSendRepository extends JpaRepository<BetterSend, String> {
    List<BetterSend> findTop10ByOrderByCreationDateDesc();
}
