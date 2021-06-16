package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.dtos.*;
import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.domains.product.Product;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductDetailsService detailService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StockService stockService;

    public Product save(Product product) {
        if(repository.existsByModel(product.getModel()))
            throw new ResourceConflictException(ResponseErrorEnum.PRD002);
        Product saved = repository.save(product);
        saved.getProductDetails().parallelStream().forEach(detail -> detail.setProductId(saved.getId()));
        detailService.saveAll(saved.getProductDetails());
        return getById(saved.getId());
    }

    public Product getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRD001));
    }

    public Page<Product> getAll(String category, Boolean status, String name, int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Product update(ProductUpdateTO dto) {
        Product product = getById(dto.getId());
        copyProperties(dto, product);
        return repository.save(product);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public StatusTO changeStatus(UUID id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRD001));
        product.setStatus(!product.getStatus());
        repository.save(product);
        return new StatusTO(product.getStatus());
    }

    public Product updateCategories(UUID id, UUID categoryId) {
        Category category = categoryService.getById(categoryId);
        Product product = this.getById(id);
        if(product.getCategory().contains(category)) {
            product.getCategory().remove(category);
            return repository.save(product);
        }
        product.getCategory().add(category);
        return repository.save(product);
    }

    public ProductDetails updateDetail(ProductDetailUpdateTO dto) {
        return detailService.update(dto);
    }

    public ProductDetails getDetailById(UUID id) {
        return detailService.getById(id);
    }

    public ProductDetails saveDetail(ProductDetailSaveTO details) {
        ProductDetails productDetails = new ProductDetails();
        copyProperties(details, productDetails);
        productDetails.setProductId(getById(details.getProductId()).getId());
        return detailService.save(productDetails);
    }

    public boolean existsByBrandId(UUID brandId) {
        return repository.existsByBrandId(brandId);
    }

    public void deleteById(UUID id) {
        detailService.deleteById(id);
    }

    public Page<Product> getByCategoryId(UUID categoryId, int page, int size) {
        return repository.findByCategoryId(categoryId, PageRequest.of(page, size));
    }

    public Product updateStock(UpdateStockTO dto) {
        stockService.updateQuantity(dto);
        return getById(dto.getProductDetailId());
    }
}
