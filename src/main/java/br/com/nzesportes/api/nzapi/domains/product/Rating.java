package br.com.nzesportes.api.nzapi.domains.product;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import lombok.*;
import org.checkerframework.common.value.qual.IntRange;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(updatable = false)
    private Customer customer;
    private UUID productId;
    private UUID purchaseId;
    @IntRange(from = 1, to = 10)
    private Integer rate;
    private String comment;
    private String title;
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
