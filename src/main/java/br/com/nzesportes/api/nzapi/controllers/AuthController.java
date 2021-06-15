package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.dtos.AuthenticationRequest;
import br.com.nzesportes.api.nzapi.dtos.AuthenticationResponse;
import br.com.nzesportes.api.nzapi.dtos.ChangePasswordTO;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${nz.allowed.origin}", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping
    public ResponseEntity<AuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
            AuthenticationResponse response = service.authenticateUser(authenticationRequest);
            return ResponseEntity.ok().body(response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest authenticationRequest) {
        return service.registerUser(authenticationRequest);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(Authentication authentication, ChangePasswordTO dto) {
        return service.changePassword(dto, (UserDetailsImpl) authentication.getPrincipal());
    }
}
