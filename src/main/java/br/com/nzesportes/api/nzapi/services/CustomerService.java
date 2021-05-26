package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.Customer;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.CustomersRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomersRepository repository;

    public Customer getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRO001));
    }

    public Customer save(Customer customer, UserDetailsImpl user) {
        if(repository.existsByUserId(user.getId()) || !user.getId().equals(customer.getUser().getId()))
            throw new ResourceConflictException(ResponseErrorEnum.PRO002);
        return repository.save(customer);
    }

    public Customer update(Customer customer, UserDetailsImpl user) {
        if(!user.getId().equals(customer.getUser().getId()))
            throw new ResourceConflictException(ResponseErrorEnum.PRO002);
        return repository.save(customer);
    }

    public Page<Customer> search(String search, int page, int size) {
        return repository.search(search,PageRequest.of(page, size));
    }

    public Customer getByUserId(UUID id) {
        return repository.findByUserId(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRO003));
    }
}
