package br.com.nzesportes.api.nzapi.services.customer;

import br.com.nzesportes.api.nzapi.domains.customer.User;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.repositories.customer.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BaseUserService {

    @Autowired
    private UsersRepository repository;

    public User getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
