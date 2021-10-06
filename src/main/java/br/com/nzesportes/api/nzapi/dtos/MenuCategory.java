package br.com.nzesportes.api.nzapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class MenuCategory {
    public String name;
    public List<SubCategoryMenuTO> subCategory;
}
