package br.com.nzesportes.api.nzapi.domains.purchase;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PurchaseItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToMany
    private Stock item;
    private Integer quantity;
    private boolean available;
}
