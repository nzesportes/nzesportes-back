package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTO {
    public List<MenuCategory> masculino;
    public List<MenuCategory> feminino;
    public List<Brand> marcas;
    public MenuTO() {
        this.masculino = new ArrayList<>();
        this.feminino = new ArrayList<>();
    }
}
