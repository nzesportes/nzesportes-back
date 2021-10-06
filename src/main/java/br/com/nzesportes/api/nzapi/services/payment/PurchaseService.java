package br.com.nzesportes.api.nzapi.services.payment;

import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.purchase.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseService {
    @Autowired
    private PurchaseRepository repository;

    public Purchase save(Purchase purchase) {
        return repository.save(purchase);
    }

    public Purchase getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }
    public Purchase getByIdAndCustomerId(UUID id, UUID customerId) {
        return repository.findByIdAndCustomerId(id, customerId).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_AUTH));
    }

}
