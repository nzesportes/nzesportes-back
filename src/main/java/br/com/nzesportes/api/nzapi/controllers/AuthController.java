package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.dtos.AuthenticationResponse;
import br.com.nzesportes.api.nzapi.dtos.AutheticationRequest;
import br.com.nzesportes.api.nzapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${nz.allowed.origin}", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AutheticationRequest autheticationRequest) {
            AuthenticationResponse response = service.AuthenticateUser(autheticationRequest);
            return ResponseEntity.ok().body(response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody AutheticationRequest autheticationRequest) {
        return service.registerUser(autheticationRequest);
    }
}
