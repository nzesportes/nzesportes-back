package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
public class PreferenceSearch {
    private String id;
    private long client_id;
    private int collector_id;
    private OffsetDateTime date_created;
}
