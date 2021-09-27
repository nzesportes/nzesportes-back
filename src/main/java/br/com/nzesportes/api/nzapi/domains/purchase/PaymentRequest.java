package br.com.nzesportes.api.nzapi.domains.purchase;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payment_requests")
public class PaymentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID buyerId;
    private String webhookStatus;
    private LocalDateTime creationDate;
    private LocalDateTime update_date;
    private LocalDateTime confirmation_date;
    private LocalDateTime cancellation_date;
}