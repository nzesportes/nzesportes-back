package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.dtos.AutheticationRequest;
import br.com.nzesportes.api.nzapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "S{nz.allowed.origin}", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@RequestBody AutheticationRequest autheticationRequest) {
        return service.AuthenticateUser(autheticationRequest);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody AutheticationRequest autheticationRequest) {
        return service.registerUser(autheticationRequest);
    }
}
