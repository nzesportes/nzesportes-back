package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.*;
import br.com.nzesportes.api.nzapi.dtos.*;
import br.com.nzesportes.api.nzapi.dtos.product.*;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.*;
import br.com.nzesportes.api.nzapi.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private ProductUtils utils;

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

    public Page<Product> getAll(String name, Boolean status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if(name == null)
            name = "";
        if(status != null)
            return repository.findByModelContainingAndStatus(name ,status, pageRequest);
        return repository.findByModelContaining(name, pageRequest);
    }

    public Product update(ProductUpdateTO dto) {
        Product product = getById(dto.getId());
        copyProperties(dto, product);
        return repository.save(product);
    }

    public StatusTO changeStatus(UUID id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRD001));
        product.setStatus(!product.getStatus());
        repository.save(product);
        return new StatusTO(product.getStatus());
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
        if(details.getSubCategoriesToAdd() != null && details.getSubCategoriesToAdd().size() > 0)
            productDetails.setSubCategories(subCategoryRepository.findAllById(details.getSubCategoriesToAdd()));
        return detailService.save(productDetails);
    }

    public boolean existsByBrandId(UUID brandId) {
        return repository.existsByBrandId(brandId);
    }

    public void deleteById(UUID id) {
        detailService.deleteById(id);
    }

    public Page<ProductDetailsTO> getAllProductDetails(String name, Gender gender, String category, String subcategory, String productSize, String color, String brand, Order order, int page, int size) {
        Pageable pageable;
        if(order != null)
            if (order.equals(Order.ASC) || order.equals(Order.DESC))
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(order.getText()), "price"));
            else
                pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "sale.percentage"));
        else
            pageable = PageRequest.of(page, size);

        List<Product> nameSearch;
        if(name != null) {
            nameSearch = repository.findByProductName(name);
            if(nameSearch != null && nameSearch.size() > 0);
                return utils.toProductDetailsPage(detailRepository.findByFilter(nameSearch, gender, category, subcategory, productSize, brand, color, pageable));
        }

        return utils.toProductDetailsPage(detailRepository.findByFilter(gender, category, subcategory, productSize, brand, color, pageable));
    }

    public Stock updateStock(UpdateStockTO dto) {
        return stockService.updateQuantity(dto);
    }
}
