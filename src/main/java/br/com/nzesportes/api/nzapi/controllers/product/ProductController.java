package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.*;
import br.com.nzesportes.api.nzapi.dtos.product.*;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.product.ProductService;
import br.com.nzesportes.api.nzapi.services.product.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin("${nz.allowed.origin}")
public class ProductController {

    @Autowired
    private ProductService service;

    @Autowired
    private StockService stockService;

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
    public Page<ProductDetailsTO> getAll(@RequestParam(required = false) Gender gender,
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

    @GetMapping("/details/purchase/{id}")
    public List<ProductDetailsTO> getByPurchaseId(@PathVariable UUID id) {
        return service.getAllProductDetailsByPurchaseId(id);
    }

    @GetMapping("/details/{id}")
    public Object getDetailById(@PathVariable UUID id, Authentication auth) {
        return service.getDetailById(id, auth != null ? (UserDetailsImpl) auth.getPrincipal() : null);
    }

    @DeleteMapping("/details/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @GetMapping("/details/stock/{id}")
    public Stock updateQuantity(@PathVariable UUID id) {
        return stockService.getById(id);
    }

    @PutMapping("/details/stock/quantity")
    public Stock updateQuantity(@RequestBody UpdateStockTO dto) {
        return service.updateQuantity(dto);
    }

    @PutMapping("/details/stock/status")
    public Stock updateStatus(@RequestBody UpdateStockTO dto) {
        return service.updateStatus(dto);
    }
}