package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.*;
import br.com.nzesportes.api.nzapi.dtos.*;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.ProductDetailRepository;
import br.com.nzesportes.api.nzapi.repositories.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductDetailRepository detailRepository;

    @Autowired
    private ProductDetailsService detailService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

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
        PageRequest pageRequest = PageRequest.of(page, size);
        if(name == null)
            name = "";
        if(category != null) {
            try {
                Category cat = categoryService.getByName(category);
                if(status != null)
                    return repository.findByModelContainingAndCategoryContainingAndStatus(name, cat, status, pageRequest);
                else
                    return repository.findByModelContainingAndCategoryContaining(name, cat, pageRequest);
            } catch (Exception e) {
                return null;
            }
        }
        if(status != null)
            return repository.findByStatus(status, pageRequest);
        return repository.findByModelContaining(name, pageRequest);
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

    public Page<ProductDetails> getAllProductDetails(Gender gender, String category, String productSize, String color, String brand, Order order, int page, int size) {
        Category cat = null;
        Brand bran = null;
        if(productSize == null)
            productSize = "";
        if(color == null)
            color = "";
        try {
            cat = categoryService.getByName(category);
            bran = brandService.getByName(brand);
        } catch (Exception e) {

        }
        Pageable pageable = PageRequest.of(page, size);
        if (Order.CHEAP.equals(order)) {
            if (cat != null)
                return detailRepository.findByFilterASC(gender, cat, productSize, color, bran, pageable);
            return detailRepository.findByFilterASC(gender, productSize, color, bran, pageable);
        }
        else if (Order.EXPENSIVE.equals(order))
            return detailRepository.findByFilterDESC(gender, cat, productSize, color, bran, pageable);
        else {
            if (cat != null)
                return detailRepository.findByFilter(gender, cat, productSize, color, bran, pageable);
            return detailRepository.findByFilter(gender, productSize, color, bran, pageable);
        }
    }
}
