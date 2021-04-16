package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.Profile;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.ProfileRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository repository;

    public Profile getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRO001));
    }

    public Profile save(Profile profile, UserDetailsImpl user) {
        if(repository.existsByUserId(user.getId()) || !user.getId().equals(profile.getUserId()))
            throw new ResourceConflictException(ResponseErrorEnum.PRO002);
        return repository.save(profile);
    }

    public Profile update(Profile profile, UserDetailsImpl user) {
        if(!user.getId().equals(profile.getUserId()))
            throw new ResourceConflictException(ResponseErrorEnum.PRO002);
        return repository.save(profile);
    }

    public Page<Profile> search(String search, int page, int size) {
        return repository.search(search,PageRequest.of(page, size));
    }

}
