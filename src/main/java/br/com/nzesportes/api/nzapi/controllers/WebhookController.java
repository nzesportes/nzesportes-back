package br.com.nzesportes.api.nzapi.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhook/create-payment")
@CrossOrigin("${nz.allowed.origin}")
public class WebhookController {

}
