package br.com.nzesportes.api.nzapi.domains.purchase;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payment_requests")
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID buyerId;
    private String webhookStatus;
    private String paymentId;
    private String preferenceId;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private LocalDateTime confirmationDate;
    private LocalDateTime cancellationDate;
}
