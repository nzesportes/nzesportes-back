package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Brand;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BrandService {
    @Autowired
    private BrandRepository repository;

    @Autowired
    private ProductDetailsService productDetailsService;

    public Brand save(Brand brand) {
        if(repository.existsByName(brand.getName()))
            throw new ResourceConflictException(ResponseErrorEnum.BRD001);
        return repository.save(brand);
    }

    public Brand update(Brand brand) {
        return this.save(brand);
    }

    public Page<Brand> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public void delete(UUID brandId) {
        if(productDetailsService.existsByBrandId(brandId))
            throw new ResourceConflictException(ResponseErrorEnum.BRD002);
        repository.deleteById(brandId);
    }

    public Brand getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.BRD003));
    }
}
