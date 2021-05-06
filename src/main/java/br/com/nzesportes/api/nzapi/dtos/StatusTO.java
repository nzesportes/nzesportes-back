package br.com.nzesportes.api.nzapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusTO {
    private Boolean status;

    public StatusTO(Boolean status) {
        this.status = status;
    }
}
