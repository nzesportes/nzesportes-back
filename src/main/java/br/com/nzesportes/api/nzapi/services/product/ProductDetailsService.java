package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.domains.product.Sale;
import br.com.nzesportes.api.nzapi.domains.product.SubCategory;
import br.com.nzesportes.api.nzapi.dtos.product.ProductDetailUpdateTO;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.ProductDetailRepository;
import br.com.nzesportes.api.nzapi.repositories.product.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ProductDetailsService {
    @Autowired
    private ProductDetailRepository repository;

    @Autowired
    private StockService stockService;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public ProductDetails getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }

    public ProductDetails getByIdUser(UUID id) {
        return repository.findByIdAndStatusTrue(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }

    public ProductDetails update(ProductDetailUpdateTO dto) {
        ProductDetails details = getById(dto.getId());
        copyProperties(dto, details);
        if(dto.getStockToAdd().size() > 0) {
            dto.getStockToAdd().forEach(stock -> stock.setProductDetail(details));
            stockService.saveAll(dto.getStockToAdd());
        }
        if(dto.getSubCategoriesToAdd() != null && dto.getSubCategoriesToAdd().size() > 0) {
            details.setSubCategories(subCategoryRepository.findAllById(dto.getSubCategoriesToAdd()));
        }
        if(dto.getSubCategoriesToRemove() != null && dto.getSubCategoriesToRemove().size() > 0) {
            List<SubCategory> subCategories = subCategoryRepository.findAllById(dto.getSubCategoriesToRemove());
            subCategories.forEach(subCategory -> {
                if (details.getSubCategories().contains(subCategory)) details.getSubCategories().remove(subCategory);
            });
        }
        return repository.save(details);
    }

    public ProductDetails save(ProductDetails details) {
        return repository.save(details);
    }

    public List<ProductDetails> saveAll(List<ProductDetails> details) {
        return repository.saveAll(details);
    }

    public List<ProductDetails> getBySubCategoryId(SubCategory subCategory){
        return repository.findBySubCategoriesContaining(subCategory);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

}
