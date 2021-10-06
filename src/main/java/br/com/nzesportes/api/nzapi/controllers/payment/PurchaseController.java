package br.com.nzesportes.api.nzapi.controllers.payment;


import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentPurchaseTO;
import br.com.nzesportes.api.nzapi.dtos.purchase.PaymentTO;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}
