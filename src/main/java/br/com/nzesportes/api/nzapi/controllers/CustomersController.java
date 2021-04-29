package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.domains.Customer;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/profiles")
@CrossOrigin("${nz.allowed.origin}")
public class CustomersController {
    @Autowired
    private CustomerService service;

    @PostMapping
    ResponseEntity<Customer> create(@RequestBody Customer customer, Authentication authentication) {
        return ResponseEntity.ok(service.save(customer, (UserDetailsImpl) authentication.getPrincipal()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Customer> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<Customer>> getById(@RequestParam String search, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(service.search(search, page, size));
    }

    @PutMapping
    ResponseEntity<Customer> update(@RequestBody Customer customer, Authentication authentication) {
        return ResponseEntity.ok(service.update(customer, (UserDetailsImpl) authentication.getPrincipal()));
    }
}
