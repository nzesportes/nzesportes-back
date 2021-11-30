package br.com.nzesportes.api.nzapi.domains.purchase;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

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
    @JoinColumn(name = "stock_id", updatable = false)
    private Stock item;
    @ManyToOne
    @JoinColumn(name = "purchase_id")
    @JsonIgnore
    private Purchase purchase;
    private Integer quantity;
    private BigDecimal cost;
    private Double discount;
    private boolean available;

    public UUID getId() {
        return id;
    }

    public Stock getItem() {
        return item;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPurchaseCost() {
        if(discount != null && discount > 0)
            return new BigDecimal("100").subtract(new BigDecimal(discount.toString()))
                    .divide(new BigDecimal("100")).multiply(item.getProductDetail().getPrice());
        else
            return item.getProductDetail().getPrice();
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Double getDiscount() {
        return discount;
    }

    public boolean isAvailable() {
        return available;
    }
}
