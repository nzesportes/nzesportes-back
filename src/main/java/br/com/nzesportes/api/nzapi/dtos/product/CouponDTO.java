package br.com.nzesportes.api.nzapi.dtos.product;

import br.com.nzesportes.api.nzapi.domains.product.Coupon;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CouponDTO {
    private Boolean status;
    private Coupon coupon;
}
