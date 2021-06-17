package br.com.nzesportes.api.nzapi.repositories;

import br.com.nzesportes.api.nzapi.domains.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Page<Contact> findByRead(Boolean read,Pageable pageable);
}
