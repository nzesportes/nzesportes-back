package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.Role;
import br.com.nzesportes.api.nzapi.domains.product.Gender;
import br.com.nzesportes.api.nzapi.domains.product.SubCategory;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository repository;

    public SubCategory save(SubCategory subCategory) {
        if(repository.existsByName(subCategory.getName()) && Objects.nonNull(subCategory))
            throw new ResourceConflictException(ResponseErrorEnum.SCT001);
        return repository.save(subCategory);
    }

    public SubCategory getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.SCT002));
    }

    public Page<SubCategory> getAll(String name, Gender gender, Boolean status, int page, int size, Authentication auth) {
        String genderSearch = gender == null ? "null" : gender.getText();
        Pageable pageable = PageRequest.of(page, size);
        if(name == null)
            name = "";
        if(auth == null || ((UserDetails) auth.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority(Role.ROLE_USER.getText())))
            return repository.findAllFilterAndStatus(name, genderSearch, true, pageable);
        else if (status == null)
            return repository.findAllFilter(name, genderSearch, pageable);
        else
            return repository.findAllFilterAndStatus(name, genderSearch, status, pageable);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
