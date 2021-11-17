package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Coupon;
import br.com.nzesportes.api.nzapi.dtos.product.CouponDTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class CouponService {
    @Autowired
    private CouponRepository repository;

    public Coupon save(Coupon coupon) {
        repository.findByCode(coupon.getCode()).ifPresent(c -> {
            throw new ResourceConflictException(ResponseErrorEnum.CONFLICTED_OPERATION);
        });
        coupon.setQuantityLeft(coupon.getQuantity());
        return repository.save(coupon);
    }
    public Coupon update(Coupon coupon) {
        Coupon toSave = getById(coupon.getId());
        copyProperties(coupon, toSave);
        return repository.save(toSave);
    }

    public Page<Coupon> getCoupons(int page, int size, String code) {
        return repository.findByFilter(code == null ? "" : code, PageRequest.of(page, size));
    }

    public Coupon getById(UUID id) {
        return repository.findAllById(id);
    }

    public CouponDTO getStatus(String code) {
        Coupon coupon = repository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        if(coupon.getQuantityLeft() > 0
                && !LocalDate.now().isBefore(coupon.getStartDate())
                && !LocalDate.now().isAfter(coupon.getEndDate()))
            return CouponDTO.builder().status(true).coupon(coupon).build();
        return CouponDTO.builder().status(false).coupon(coupon).build();
    }

    public Coupon getByCode(String code) {
        return repository.findByCode(code).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }
}
