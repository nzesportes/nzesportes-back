package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.dtos.StatusTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.CategoryRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository repository;

    public Category save(Category category) {
        if(repository.existsByName(category.getName()))
            throw new ResourceConflictException(ResponseErrorEnum.CAT001);
        return repository.save(category);
    }

    public Page<Category> getAll(int page, int size, UserDetailsImpl principal) {
        principal.getAuthorities().forEach(grantedAuthority -> System.out.println(grantedAuthority));
        return repository.findAll(PageRequest.of(page, size));
    }

    public HttpStatus deleteById(UUID id) {
        repository.deleteById(id);
        return HttpStatus.OK;
    }

    public Category update(Category category) {
        return repository.save(category);
    }

    public Category getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.CAT002));
    }

    public StatusTO changeStatus(UUID id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.CAT002));
        category.setStatus(!category.getStatus());
        return new StatusTO(repository.save(category).getStatus());
    }
}
