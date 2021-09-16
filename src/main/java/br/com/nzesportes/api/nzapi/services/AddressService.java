package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.Address;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResourceUnauthorizedException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.AddressRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressRepository repository;

    public Address save(Address address, UserDetailsImpl user) {
         address.setCustomerId(customerService.getByUserId(user.getId()).getId());
         return repository.save(address);
    }

    public Address getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.ADR001));
    }

    public List<Address> getByUser(UserDetailsImpl principal) {
        return repository.findByCustomerId(customerService.getByUserId(principal.getId()).getId());
    }

    public ResponseEntity<?> deleteById(UUID id, UserDetailsImpl principal) {
        Address address = getById(id);
        if(address.getCustomerId().equals(customerService.getByUserId(principal.getId()).getId())) {
            repository.deleteById(id);
            return ResponseEntity.status(201).body(null);
        }
        throw new ResourceUnauthorizedException(ResponseErrorEnum.ADR002);
    }

    public Address update(Address address, UserDetailsImpl principal) {
        if(address.getCustomerId().equals(customerService.getByUserId(principal.getId()).getId())) {
            return repository.save(address);
        }
        throw new ResourceUnauthorizedException(ResponseErrorEnum.ADR002);
    }
}
