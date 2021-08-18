package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.Order;
import br.com.nzesportes.api.nzapi.domains.product.Product;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.dtos.ProductDetailSaveTO;
import br.com.nzesportes.api.nzapi.dtos.ProductDetailUpdateTO;
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
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Page<Product> getAll(@RequestParam(required = false) Boolean status,
                                @RequestParam(required = false) String name,
                                @RequestParam int page,
                                @RequestParam int size) {
        return service.getAll(name, status, page, size);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Product update(@RequestBody ProductUpdateTO dto) {
        return service.update(dto);
    }


    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<?> changeStatus(@PathVariable UUID id) {
        return ResponseEntity.ok(service.changeStatus(id));
    }

    /*
    * DETALHES DE PRODUTO
    * */

    @PostMapping("/details")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductDetails saveDetail(@RequestBody ProductDetailSaveTO details) {
        return service.saveDetail(details);
    }

    @PutMapping("/details")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ProductDetails updateDetails(@RequestBody ProductDetailUpdateTO details) {
        return service.updateDetail(details);
    }

    @GetMapping("/details")
    public Page<ProductDetails> getAll(@RequestParam(required = false) Gender gender,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) String category,
                                       @RequestParam(required = false) String subcategory,
                                       @RequestParam(required = false) String productSize,
                                       @RequestParam(required = false) String color,
                                       @RequestParam(required = false) String brand,
                                       @RequestParam(required = false) Order order,
                                       @RequestParam int page,
                                       @RequestParam int size) {
        return service.getAllProductDetails(name, gender, category, subcategory, productSize, color, brand, order, page, size);
    }

    @GetMapping("/details/{id}")
    public ProductDetails getDetailById(@PathVariable UUID id) {
        return service.getDetailById(id);
    }

    @DeleteMapping("/details/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

}