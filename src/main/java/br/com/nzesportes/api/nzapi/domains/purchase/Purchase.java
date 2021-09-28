package br.com.nzesportes.api.nzapi.domains.purchase;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_requests")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Customer customer;
    @OneToMany
    private List<PurchaseItems> items;
    private BigDecimal shipment;
    private BigDecimal totalCost;
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private PaymentRequest paymentRequest;

    public Purchase() {

    }
}
