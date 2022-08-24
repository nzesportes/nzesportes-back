package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.LayoutImages;
import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.SubCategory;
import br.com.nzesportes.api.nzapi.dtos.MenuCategory;
import br.com.nzesportes.api.nzapi.dtos.MenuTO;
import br.com.nzesportes.api.nzapi.dtos.SizeTO;
import br.com.nzesportes.api.nzapi.dtos.SubCategoryMenuTO;
import br.com.nzesportes.api.nzapi.repositories.LayoutImagesRepository;
import br.com.nzesportes.api.nzapi.repositories.product.BrandRepository;
import br.com.nzesportes.api.nzapi.repositories.product.CategoryRepository;
import br.com.nzesportes.api.nzapi.repositories.product.SubCategoryRepository;
import br.com.nzesportes.api.nzapi.services.product.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class MenuService {
    private final static String IMAGE_ID = "IMAGES";

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private StockService stockService;

    @Autowired
    private LayoutImagesRepository imagesRepository;

    public MenuTO getMenu() {
        List<Category> male;
        male = categoryRepository.findCategoriesBySubCategoryGender(Gender.MALE);
        List<Category> female;
        female = categoryRepository.findCategoriesBySubCategoryGender(Gender.FEMALE);

        return buildMenu(male, female);
    }

    public MenuTO buildMenu(List<Category> male, List<Category> female) {
        MenuTO menuTO = new MenuTO();
        male.forEach(m -> menuTO.getMasculino().add(new MenuCategory(m.getName(), getSubCategory(m, Gender.MALE))));
        female.forEach(m -> menuTO.getFeminino().add(new MenuCategory(m.getName(), getSubCategory(m, Gender.FEMALE))));
        menuTO.setMarcas(brandRepository.findOnMenu());
        LayoutImages images = getImages();
        if(images != null) {
            menuTO.setBannerImages(images.getBannerImages());
            menuTO.setSlideImages(images.getSlideImages());
        }
        return menuTO;
    }

    private List<SubCategoryMenuTO> getSubCategory(Category c, Gender gender) {
        List<SubCategoryMenuTO> subCategoryMenu = new ArrayList<>();
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryMenu(c, gender);
        subCategories.stream().forEach(subCategory -> subCategoryMenu.add(new SubCategoryMenuTO(subCategory.getId(), subCategory.getName())));
        return subCategoryMenu;
    }

    public LayoutImages getImages() {
        return imagesRepository.findById(IMAGE_ID).orElse(null);
    }

    public LayoutImages saveImages(LayoutImages images) {
        LayoutImages img = imagesRepository.findById(IMAGE_ID).orElse(null);
        if(img != null) {
            img.setSlideImages(images.getSlideImages());
            img.setBannerImages(images.getBannerImages());
            return imagesRepository.save(img);
        }
        images.setId(IMAGE_ID);
        return imagesRepository.save(images);
    }

    public List<SizeTO> getSizes() {
        return stockService.getSizes();
    }
}
