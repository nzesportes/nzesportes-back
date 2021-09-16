package br.com.nzesportes.api.nzapi.controllers.payment;


import br.com.nzesportes.api.nzapi.dtos.PaymentTO;
import br.com.nzesportes.api.nzapi.services.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@CrossOrigin("${nz.allowed.origin}")
public class PaymentController {
    @Autowired
    private PaymentService service;

    @PostMapping("/request")
    public Object createPaymentRequest(PaymentTO dto) {
        return service.createPaymentRequest(dto);
    }
}
