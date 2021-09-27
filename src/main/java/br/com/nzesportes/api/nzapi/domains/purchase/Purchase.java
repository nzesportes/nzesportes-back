package br.com.nzesportes.api.nzapi.domains.purchase;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private Customer customer;
    private List<PurchaseItems> items;
    private BigDecimal shipment;
    private BigDecimal totalCost;
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;
}
