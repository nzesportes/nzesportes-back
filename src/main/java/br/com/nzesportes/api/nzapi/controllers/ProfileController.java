package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.domains.Profile;
import br.com.nzesportes.api.nzapi.domains.User;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/profiles")
@CrossOrigin("S{allowed.origin}")
public class ProfileController {
    @Autowired
    private ProfileService service;

    @PostMapping
    ResponseEntity<Profile> save(@RequestBody Profile profile, Authentication authentication) {
        return ResponseEntity.ok(service.save(profile, (UserDetailsImpl) authentication.getPrincipal()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Profile> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<Page<Profile>> getById(@RequestParam String search, @RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(service.search(search, page, size));
    }

    @PutMapping
    ResponseEntity<Profile> update(@RequestBody Profile profile, Authentication authentication) {
        return ResponseEntity.ok(service.update(profile, (UserDetailsImpl) authentication.getPrincipal()));
    }
}
