package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.Role;
import br.com.nzesportes.api.nzapi.domains.User;
import br.com.nzesportes.api.nzapi.dtos.AdminSaveTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.UsersRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UsersRepository repository;

    public User save(AdminSaveTO dto) {
        if(repository.existsByUsername(dto.getUsername()))
            throw new ResourceConflictException(ResponseErrorEnum.AUTH001);
        User user = new User(dto.getUsername(), null, dto.getRole());
        return repository.save(user);
    }

    public User getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.USR001));
    }

    public Page<User> getAdmins(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public void deleteById(UUID id, UserDetailsImpl principal) {
        User user = getById(id);
        if(user.getId().equals(principal.getId()))
            throw new ResourceConflictException(ResponseErrorEnum.USR002);
    }

    public User update(AdminSaveTO dto) {
        User user = getById(dto.getId());
        user.setUsername(dto.getUsername());
        dto.setRole(dto.getRole());
        return repository.save(user);
    }
}
