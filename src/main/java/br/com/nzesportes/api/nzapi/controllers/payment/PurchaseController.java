package br.com.nzesportes.api.nzapi.controllers.payment;


import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentPurchaseTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PurchaseTO;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/purchase")
@CrossOrigin("${nz.allowed.origin}")
public class PurchaseController {
    @Autowired
    private PaymentService service;

    @PostMapping
    public PaymentPurchaseTO createPaymentRequest(@RequestBody PaymentTO dto, Authentication auth) {
        return service.createPaymentRequest(dto, (UserDetailsImpl) auth.getPrincipal());
    }

    @GetMapping("/customers/{customerId}")
    public Page<Purchase> getAllByCustomerId(@RequestParam int page, @RequestParam int size, @PathVariable UUID customerId) {
        return service.getAllByCustomerId(customerId, page, size);
    }

    @GetMapping("/{id}")
    public Purchase getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<Purchase> getAll(@RequestParam int page, @RequestParam int size, Authentication auth) {
        return service.getAll(page, size, (UserDetailsImpl) auth.getPrincipal());
    }
}
