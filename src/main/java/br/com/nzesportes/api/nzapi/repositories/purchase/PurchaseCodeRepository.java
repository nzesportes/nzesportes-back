package br.com.nzesportes.api.nzapi.repositories.purchase;

import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface PurchaseCodeRepository extends JpaRepository<PurchaseCode, BigInteger> {
}
