package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.ProductSize;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductSizeService {
    @Autowired
    private ProductSizeRepository repository;

    public ProductSize save(ProductSize productSize) {
        return repository.save(productSize);
    }

    public ProductSize update(ProductSize productSize) {
        return repository.save(productSize);
    }

    public List<ProductSize> getAll() {
        return repository.findAll();
    }

    public ProductSize getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
