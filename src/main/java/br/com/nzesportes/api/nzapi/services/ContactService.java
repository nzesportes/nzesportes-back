package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.Contact;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ContactService {
    @Autowired
    private ContactRepository repository;

    public Contact save(Contact contact) {
        return repository.save(contact);
    }

    public Contact getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }

    public Page<Contact> getAll(Boolean read, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if(read != null)
            return repository.findByRead(read, pageable);
        return repository.findAll(pageable);
    }

    public Contact updateRead(UUID id) {
        Contact contact = getById(id);
        contact.setRead(!contact.getRead());
        return repository.save(contact);
    }
}
