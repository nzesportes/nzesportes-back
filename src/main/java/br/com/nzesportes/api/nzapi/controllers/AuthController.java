package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.dtos.AuthenticationResponse;
import br.com.nzesportes.api.nzapi.dtos.LoginTO;
import br.com.nzesportes.api.nzapi.security.JwtUtils;
import br.com.nzesportes.api.nzapi.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import br.com.nzesportes.api.nzapi.domains.User;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "S{nz.allowed.origin}", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService service;

    @Autowired
    private JwtUtils jwtUtils;

    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginTO loginTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginTO.getUsername(), loginTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }
}
