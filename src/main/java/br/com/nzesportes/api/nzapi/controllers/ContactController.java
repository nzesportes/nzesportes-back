package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.domains.Contact;
import br.com.nzesportes.api.nzapi.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/contact")
@CrossOrigin("${nz.allowed.origin}")
public class ContactController {
    @Autowired
    private ContactService service;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> save(@RequestBody Contact contact) {
        return ResponseEntity.status(201).body(service.save(contact));
    }

    @GetMapping("/{id}")
    public Contact getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<Contact> getAll(@RequestParam(required = false) Boolean read, @RequestParam int page, @RequestParam int size) {
        return service.getAll(read, page, size);
    }

    @PutMapping("/{id}/read")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRead(@PathVariable UUID id) {
        return ResponseEntity.ok(service.updateRead(id));
    }
}
