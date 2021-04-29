package br.com.nzesportes.api.nzapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin("${nz.allowed.origin}")
public class UsersController {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> toBeImplemented() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("To be implemented");
    }
}
