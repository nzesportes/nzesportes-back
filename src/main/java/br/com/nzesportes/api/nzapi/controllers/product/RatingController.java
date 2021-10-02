package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Rating;
import br.com.nzesportes.api.nzapi.dtos.product.RatingSaveTO;
import br.com.nzesportes.api.nzapi.dtos.product.RatingUpdateTO;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.product.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("/ratings")
public class RatingController {
    @Autowired
    private RatingService service;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Rating create(@RequestBody RatingSaveTO dto, Authentication authentication) {
        return service.save(dto, (UserDetailsImpl) authentication.getPrincipal());
    }

    @PutMapping
    public Rating update(@RequestBody RatingUpdateTO dto, Authentication authentication) {
        return service.update(dto, (UserDetailsImpl) authentication.getPrincipal());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Page<Rating> getRatings(@RequestParam int page, @RequestParam int size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public Rating getRatingById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping("/user")
    public Page<Rating> getRatings(@RequestParam int page, @RequestParam int size, Authentication authentication) {
        return service.getAll(page, size, (UserDetailsImpl) authentication.getPrincipal());
    }

    @GetMapping("/product/{id}")
    public Page<Rating> getRatingsByProductId(@PathVariable UUID id, @RequestParam int page, @RequestParam int size) {
        return service.getAllByProductId(id, page, size);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id,  Authentication authentication) {
        service.deleteById(id, (UserDetailsImpl) authentication.getPrincipal());
    }
}
