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
@Table(name = "purchase_items")
public class PurchaseItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock item;
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
    private Integer quantity;
    private BigDecimal cost;
    private boolean available;
}
