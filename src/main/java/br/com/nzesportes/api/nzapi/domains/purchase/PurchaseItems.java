package br.com.nzesportes.api.nzapi.domains.purchase;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_requests")
public class PurchaseItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Stock item;
    private Integer quantity;
    private BigDecimal cost;
    private boolean available;
}
