package br.com.nzesportes.api.nzapi.controllers.melhorenvio;

import br.com.nzesportes.api.nzapi.domains.product.Size;
import br.com.nzesportes.api.nzapi.services.melhorenvio.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/better-send/sizes")
@CrossOrigin("${nz.allowed.origin}")
public class SizeController {
    @Autowired
    private SizeService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Size save(@RequestBody Size size) {
        return service.save(size);
    }

    @GetMapping
    public Page<Size> getAll(@RequestParam(required = false) String type, @RequestParam int page, @RequestParam int size) {
        return service.getAll(type, page, size);
    }

    @GetMapping("/{id}")
    public Size getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERATOR')")
    public Size update(@RequestBody Size size) {
        return service.update(size);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        service.deleteById(id);
    }

}
