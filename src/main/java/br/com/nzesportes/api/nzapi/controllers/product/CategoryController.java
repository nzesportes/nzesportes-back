package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.dtos.StatusTO;
import br.com.nzesportes.api.nzapi.services.product.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/products/categories")
@CrossOrigin("${nz.allowed.origin}")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(category));
    }

    @GetMapping
    public Page<Category> getCategories(@RequestParam int page,
                                        @RequestParam int size,
                                        @RequestParam(required = false) Boolean status,
                                        @RequestParam(required = false) String type,
                                        @RequestParam(required = false) String name, Authentication auth) {
        return service.getAll(page, size, status, type, name, (UserDetails) auth.getPrincipal());
    }

    @GetMapping("/{id}")
    public Category getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Category update(@RequestBody Category category) {
        return service.update(category);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<StatusTO> changeStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(service.changeStatus(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpStatus deleteById(@PathVariable UUID id) {
        return service.deleteById(id);
    }
}
