package br.com.nzesportes.api.nzapi.feign;

import br.com.nzesportes.api.nzapi.dtos.mercadopago.payment.PaymentMPTO;
import br.com.nzesportes.api.nzapi.dtos.mercadopago.preference.Preference;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mercadoPagoClient", url = "${nz.mercado-pago.url}")
public interface MercadoPagoClient {

    @PostMapping("/checkout/preferences")
    Preference createPreference(@RequestHeader("Authorization") String token, @RequestBody Preference preference);

    @GetMapping("/v1/payments/{id}")
    PaymentMPTO getPayment(@RequestHeader("Authorization") String token, @PathVariable String id);

    @GetMapping("/v1/payments/search")
    PaymentMPTO getPayment(@RequestHeader("Authorization") String token,
                           @RequestParam("sort") String sort,
                           @RequestParam("criteria") String criteria,
                           @RequestParam("external_reference") String external_reference);
}
