package br.com.nzesportes.api.nzapi.controllers.payment;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook")
@CrossOrigin("${nz.allowed.origin}")
public class WebhookController {

}
