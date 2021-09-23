package br.com.nzesportes.api.nzapi.controllers;

import br.com.nzesportes.api.nzapi.dtos.MenuTO;
import br.com.nzesportes.api.nzapi.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@CrossOrigin("${nz.allowed.origin}")
public class MenuController {
    @Autowired
    private MenuService service;

    @GetMapping
    public MenuTO getMenu() {
        return service.getMenu();
    }
}
