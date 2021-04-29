package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Category;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.CategoryRepository;
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
            throw new ResourceConflictException(ResponseErrorEnum.CATO001);
        return repository.save(category);
    }

    public Page<Category> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public HttpStatus deleteById(UUID id) {
        repository.deleteById(id);
        return HttpStatus.OK;
    }

    public Category update(Category category) {
        return repository.save(category);
    }
}
