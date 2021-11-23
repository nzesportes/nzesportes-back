package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.ProductSize;
import br.com.nzesportes.api.nzapi.services.product.ProductSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products-size")
@CrossOrigin("${nz.allowed.origin}")
public class ProductSizeController {

    @Autowired
    private ProductSizeService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductSize create(@RequestBody ProductSize productSize) {
        return service.save(productSize);
    }

    @GetMapping("/{id}")
    public ProductSize getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<ProductSize> getAll() { return service.getAll(); }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductSize update(@RequestBody ProductSize productSize) {
        return service.update(productSize);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteById(@PathVariable UUID id) { service.deleteById(id); }

}
