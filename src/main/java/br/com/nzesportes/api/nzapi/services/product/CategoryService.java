package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.Role;
import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.dtos.StatusTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

    public Page<Category> getAll(int page, int size, Boolean status, String type, String name, UserDetails principal) {
        if(type == null)
            type = "";
        if(name == null)
            name = "";
        if(principal.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_USER.getText())))
            return repository.findByFilterAndStatus(true, type, name, PageRequest.of(page, size));
        if(status != null && principal.getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getText())))
            return repository.findByFilterAndStatus(status, type, name, PageRequest.of(page, size));
        return repository.findByFilter(type, name, PageRequest.of(page, size));
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
