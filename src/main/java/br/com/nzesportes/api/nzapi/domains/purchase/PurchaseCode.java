package br.com.nzesportes.api.nzapi.domains.purchase;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "purchase_code")
public class PurchaseCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;
    private UUID purchaseId;
}
