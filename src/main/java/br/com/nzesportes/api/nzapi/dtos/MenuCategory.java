package br.com.nzesportes.api.nzapi.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MenuCategory {
    public String name;
    public List<SubCategoryMenuTO> subCategory;
}
