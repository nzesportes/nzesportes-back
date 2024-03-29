package br.com.nzesportes.api.nzapi.domains.purchase;

import br.com.nzesportes.api.nzapi.domains.customer.Address;
import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.product.Coupon;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Customer customer;

    @OneToMany(mappedBy = "purchase", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PurchaseItems> items;
    private BigDecimal shipment;

    @ManyToOne
    private Address shipmentAddress;
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    private MercadoPagoPaymentStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_request_id")
    private PaymentRequest paymentRequest;
    private Integer shipmentServiceId;
    private BigInteger code;
    @ManyToOne
    private Coupon coupon;
    private Boolean tag;
    private Boolean ticket;

    @PrePersist
    private void prePersist() {
        this.tag = false;
        if(this.items != null && this.items.size() > 0)
            this.items.forEach(i -> i.setPurchase(this));
    }
}
