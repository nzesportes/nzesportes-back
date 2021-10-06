package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.product.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products/brands")
@CrossOrigin("${nz.allowed.origin}")
public class BrandController {
    @Autowired
    private BrandService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(allEntries = true, value = "menu")
    public Brand create(@RequestBody Brand brand) {
        return service.save(brand);
    }

    @GetMapping
    public Page<Brand> getAll(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String name, Authentication authentication) {
        return service.getAll(name, page, size, (UserDetailsImpl) authentication.getPrincipal());
    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(allEntries = true, value = "menu")
    public Brand update(@RequestBody Brand brand) {
        return service.update(brand);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @CacheEvict(allEntries = true, value = "menu")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
