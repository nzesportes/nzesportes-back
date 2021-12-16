package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.domains.LayoutImages;
import br.com.nzesportes.api.nzapi.dtos.MenuTO;
import br.com.nzesportes.api.nzapi.dtos.SizeTO;
import br.com.nzesportes.api.nzapi.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin("${nz.allowed.origin}")
public class MenuController {
    @Autowired
    private MenuService service;

    @GetMapping
    @Cacheable("menu")
    public MenuTO getMenu() {
        return service.getMenu();
    }

    @GetMapping("/images")
    public LayoutImages getImages() {
        return service.getImages();
    }

    @PostMapping("/images")
    @CacheEvict(allEntries = true, value = "menu")
    public LayoutImages saveImages(@RequestBody LayoutImages images) {
        return service.saveImages(images);
    }

    @GetMapping("/sizes")
    public List<SizeTO> getSizes() {
        return service.getSizes();
    }
}
