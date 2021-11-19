package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Sale;
import br.com.nzesportes.api.nzapi.services.product.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products/details/sales")
@CrossOrigin("${nz.allowed.origin}")
public class SaleController {
    @Autowired
    private SaleService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Sale create(@RequestBody Sale sale) {
        return service.save(sale);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Page<Sale> getAll(@RequestParam int page, @RequestParam int size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public Sale getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }
}
