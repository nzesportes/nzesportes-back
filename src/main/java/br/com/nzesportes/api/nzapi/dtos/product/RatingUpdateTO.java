package br.com.nzesportes.api.nzapi.dtos.product;

import lombok.Data;

import java.util.UUID;

@Data
public class RatingUpdateTO {
    private UUID id;
    private Integer rate;
    private String comment;
}
