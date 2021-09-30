package br.com.nzesportes.api.nzapi.feign;

import br.com.nzesportes.api.nzapi.dtos.mercadopago.payment.PaymentMPTO;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.preference.Preference;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mercadoPagoClient", url = "${nz.mercado-pago.url}")
public interface MercadoPagoClient {

    @PostMapping("/checkout/preferences")
    Preference createPreference(@RequestHeader("Authorization") String token, @RequestBody Preference preference);

    @GetMapping("/v1/payments/{id}")
    PaymentMPTO getPayment(@RequestHeader("Authorization") String token, @PathVariable String id);
}
