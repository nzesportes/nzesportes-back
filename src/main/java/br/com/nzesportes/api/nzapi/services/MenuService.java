package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.SubCategory;
import br.com.nzesportes.api.nzapi.dtos.MenuCategory;
import br.com.nzesportes.api.nzapi.dtos.MenuTO;
import br.com.nzesportes.api.nzapi.dtos.SubCategoryMenuTO;
import br.com.nzesportes.api.nzapi.repositories.product.CategoryRepository;
import br.com.nzesportes.api.nzapi.repositories.product.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public MenuTO getMenu() {
        List<Category> male;
        male = categoryRepository.findCategoriesBySubCategoryGender(Gender.MALE);
        List<Category> female;
        female = categoryRepository.findCategoriesBySubCategoryGender(Gender.FEMALE);
        return buildMenu(male, female);
    }

    public MenuTO buildMenu(List<Category> male, List<Category> female) {
        MenuTO menuTO = new MenuTO();
        male.forEach(m -> menuTO.getMasculino().add(new MenuCategory(m.getName(), getMaleSubCategory(m))));
        return menuTO;
    }

    private List<SubCategoryMenuTO> getMaleSubCategory(Category m) {
        List<SubCategoryMenuTO> subCategoryMenu = new ArrayList<>();
        List<SubCategory> subCategories = subCategoryRepository.findByCategory(m);
        subCategories.parallelStream().forEach(subCategory -> subCategoryMenu.add(new SubCategoryMenuTO(subCategory.getId(), subCategory.getName())));
        return subCategoryMenu;
    }
}
