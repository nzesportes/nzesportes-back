package br.com.nzesportes.api.nzapi.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuTO {
    public List<MenuCategory> masculino;
    public List<MenuCategory> feminino;

    public MenuTO() {
        this.masculino = new ArrayList<>();
        this.feminino = new ArrayList<>();
    }
}
