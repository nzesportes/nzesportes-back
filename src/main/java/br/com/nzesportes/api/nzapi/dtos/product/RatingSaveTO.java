package br.com.nzesportes.api.nzapi.dtos.product;

import lombok.Data;
import org.checkerframework.common.value.qual.IntRange;

import java.util.UUID;

@Data
public class RatingSaveTO {
    private UUID purchaseId;
    private UUID productId;
    @IntRange(from = 1, to = 5)
    private Integer rate;
    private String comment;
    private String title;
}
