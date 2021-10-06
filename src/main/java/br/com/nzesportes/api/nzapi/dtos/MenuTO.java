package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<MenuCategory> masculino;
    private List<MenuCategory> feminino;
    private String slideImages;
    private String bannerImages;
    private List<Brand> marcas;
    public MenuTO() {
        this.masculino = new ArrayList<>();
        this.feminino = new ArrayList<>();
    }
}
