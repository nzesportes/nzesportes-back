package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.services.BetterSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/better-send")
@CrossOrigin(origins = "${nz.allowed.origin}")
public class BetterSendController {

    @Autowired
    private BetterSendService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> postToken(@RequestHeader("code") String code) {
        return this.service.postToken(code);
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateShipping(@RequestBody Object request) {
        return this.service.calculateShipping(request);
    }

    @GetMapping("/validation")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> isTokenValid(){
        return this.service.isValidToken();
    }
}
