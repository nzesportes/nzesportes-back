package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Coupon;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CouponService {
    @Autowired
    private CouponRepository repository;

    public Coupon save(Coupon coupon) {
        coupon.setQuantityLeft(coupon.getQuantity());
        return repository.save(coupon);
    }
    public Coupon update(Coupon coupon) {
        return repository.save(coupon);
    }

    public Page<Coupon> getCoupons(int page, int size, String code) {
        return repository.findByFilter(code == null ? "" : code, PageRequest.of(page, size));
    }

    public Coupon getById(UUID id) {
        return repository.findAllById(id);
    }

    public Boolean getStatus(String code) {
        Coupon coupon = repository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        if(coupon.getQuantityLeft() > 0 && !LocalDate.now().isBefore(coupon.getEndDate()))
            return true;
        return false;
    }

    public Coupon getByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }
}
