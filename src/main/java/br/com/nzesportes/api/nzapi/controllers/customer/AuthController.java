package br.com.nzesportes.api.nzapi.controllers.customer;

import br.com.nzesportes.api.nzapi.dtos.customer.AuthenticationRequest;
import br.com.nzesportes.api.nzapi.dtos.customer.AuthenticationResponse;
import br.com.nzesportes.api.nzapi.dtos.customer.ChangePasswordTO;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.customer.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


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

    @PostMapping("/{flow}")
    public ResponseEntity<?> createFlow(@RequestBody AuthenticationRequest authenticationRequest, @PathVariable String flow) {
        return service.createFlow(authenticationRequest, flow);
    }

    @PutMapping("/{flow}/{id}")
    public ResponseEntity<AuthenticationResponse> firstAccess(@PathVariable UUID id, @RequestBody ChangePasswordTO dto, @PathVariable String flow) {
        return service.performFlow(id, dto, flow);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordTO dto, Authentication authentication) {
        return service.changePassword(dto, (UserDetailsImpl) authentication.getPrincipal());
    }
}
