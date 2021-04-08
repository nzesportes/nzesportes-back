package br.com.nzesportes.api.nzapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("S{allowed.origin}")
public class UsersController {
    @GetMapping
    public ResponseEntity<String> toBeImplemented() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("To be implemented");
    }
}
