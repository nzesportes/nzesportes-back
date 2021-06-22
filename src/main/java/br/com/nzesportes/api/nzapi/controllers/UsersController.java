package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.domains.User;
import br.com.nzesportes.api.nzapi.dtos.AdminSaveTO;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin("${nz.allowed.origin}")
public class UsersController {
    @Autowired
    private UserService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveUser(@RequestBody AdminSaveTO dto) {
        return ResponseEntity.status(201).body(service.save(dto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<User> getAdmins(@RequestParam int page, @RequestParam int size) {
        return service.getAdmins(page, size);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public User update(@RequestBody AdminSaveTO dto) {
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id, Authentication authentication) {
        service.deleteById(id, (UserDetailsImpl) authentication.getPrincipal());
    }
}
