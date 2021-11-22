package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.customer.Role;
import br.com.nzesportes.api.nzapi.domains.product.Brand;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.BrandRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BrandService {
    @Autowired
    private BrandRepository repository;

    @Autowired
    private ProductService productService;

    public Brand save(Brand brand) {
        if(repository.existsByName(brand.getName()))
            throw new ResourceConflictException(ResponseErrorEnum.BRD001);
        return repository.save(brand);
    }

    public Brand update(Brand brand) {
        return repository.save(brand);
    }

    public Page<Brand> getAll(String name, int page, int size, UserDetailsImpl principal) {
        if(principal != null && principal.getAuthorities() != null && principal.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_USER.getText())))
            return repository.findByFilterAndStatusTrue(name == null ? "" : name, PageRequest.of(page, size));
        return repository.findByFilter(name == null ? "" : name, PageRequest.of(page, size));
    }

    public void delete(UUID brandId) {
        if(productService.existsByBrandId(brandId))
            throw new ResourceConflictException(ResponseErrorEnum.BRD002);
        repository.deleteById(brandId);
    }

    public Brand getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_AUTH));
    }

}
