package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.domains.customer.Address;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.customer.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/addresses")
@CrossOrigin(origins = "${nz.allowed.origin}")
public class AddressController {
    @Autowired
    private AddressService service;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Address save(@RequestBody Address address,  Authentication authentication) {
        return service.save(address, (UserDetailsImpl) authentication.getPrincipal());
    }

    @GetMapping
    public List<Address> getByUser(Authentication authentication) {
        return service.getByUser((UserDetailsImpl) authentication.getPrincipal());
    }

    @GetMapping("/{id}")
    public Address getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping
    public Address update(@RequestBody Address address, Authentication authentication) {
        return service.update(address, (UserDetailsImpl) authentication.getPrincipal());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(Authentication authentication, @PathVariable UUID id) {
        return service.deleteById(id, (UserDetailsImpl) authentication.getPrincipal());
    }
}
