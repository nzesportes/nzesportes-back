package br.com.nzesportes.api.nzapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class MenuCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    public String name;
    public List<SubCategoryMenuTO> subCategory;
}
