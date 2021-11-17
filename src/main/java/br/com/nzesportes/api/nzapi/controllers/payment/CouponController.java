package br.com.nzesportes.api.nzapi.controllers.payment;

import br.com.nzesportes.api.nzapi.domains.product.Coupon;
import br.com.nzesportes.api.nzapi.dtos.product.CouponDTO;
import br.com.nzesportes.api.nzapi.services.product.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coupons")
@CrossOrigin("${nz.allowed.origin}")
public class CouponController {
    @Autowired
    private CouponService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Coupon create(@RequestBody Coupon coupon) { return service.save(coupon); }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Coupon update(@RequestBody Coupon coupon) { return service.update(coupon); }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Page<Coupon> getCoupons(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String  code) {
        return service.getCoupons(page, size, code);
    }

    @GetMapping("/validate/{code}")
    public CouponDTO validate(@PathVariable String code) {
        return service.getStatus(code);
    }

    @GetMapping("/{id}")
    public Coupon getById(@PathVariable UUID id) {
        return service.getById(id);
    }
}
