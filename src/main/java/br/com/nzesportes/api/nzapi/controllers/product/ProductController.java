package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Product;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.dtos.ProductUpdateTO;
import br.com.nzesportes.api.nzapi.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin("${nz.allowed.origin}")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<Product> getAll(@RequestParam int page, @RequestParam int size) {
        return service.getAll(page, size);
    }

    @PutMapping
    public Product update(@RequestBody ProductUpdateTO dto) {
        return service.update(dto);
    }

    @GetMapping("/details/{id}")
    public ProductDetails getDetailById(@PathVariable UUID id) {
        return service.getDetailById(id);
    }

    @PutMapping("/details")
    public ProductDetails update(@RequestBody ProductDetailUpdateTO dto) {
        return service.updateDetail(dto);
    }

    @PutMapping("/{id}/category/{categoryId}")
    public Product updateCategories(@PathVariable UUID id, @PathVariable UUID categoryId) {
        return service.updateCategories(id, categoryId);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(service.changeStatus(id));
    }
}