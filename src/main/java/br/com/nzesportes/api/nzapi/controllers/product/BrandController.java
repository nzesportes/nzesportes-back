package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import br.com.nzesportes.api.nzapi.services.product.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public Brand create(@RequestBody Brand brand) {
        return service.save(brand);
    }

    @GetMapping
    public Page<Brand> getAll(@RequestParam int page, @RequestParam int size, @RequestParam(required = false) String name) {
        return service.getAll(name, page, size);
    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Brand update(@RequestBody Brand brand) {
        return service.update(brand);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
