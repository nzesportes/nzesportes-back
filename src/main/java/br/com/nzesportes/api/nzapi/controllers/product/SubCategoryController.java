package br.com.nzesportes.api.nzapi.controllers.product;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.SubCategory;
import br.com.nzesportes.api.nzapi.dtos.SubCategorySaveTO;
import br.com.nzesportes.api.nzapi.services.product.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products/sub-categories")
@CrossOrigin("${nz.allowed.origin}")
public class SubCategoryController {
    @Autowired
    private SubCategoryService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<SubCategory> create(@RequestBody SubCategorySaveTO subCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(subCategory));
    }

    @GetMapping("/{id}")
    public SubCategory getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<SubCategory> getAll(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) Gender gender,
                                    @RequestParam(required = false) Boolean status,
                                    @RequestParam int page,
                                    @RequestParam int size, Authentication auth) {
        return service.getAll(name, gender, status, page, size, auth);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @PutMapping
    public ResponseEntity<SubCategory> update(@RequestBody SubCategorySaveTO subCategory) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(subCategory));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        service.deleteById(id);
    }
}
