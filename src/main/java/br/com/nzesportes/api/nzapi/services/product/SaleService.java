package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Sale;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SaleService {
    @Autowired
    private SaleRepository repository;

    public Sale save(Sale sale) {
        Page<Sale> sales = repository.findByProductDetailIdOrderByEndDateDesc(sale.getProductDetailId(), PageRequest.of(0, 1));
        if((!sales.isEmpty() && sales.getContent().get(0).getEndDate().isAfter(sale.getStartDate())) || sale.getStartDate().isAfter(sale.getEndDate()))
            throw new ResourceConflictException(ResponseErrorEnum.SAL001);
        return repository.save(sale);
    }

    public Page<Sale> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public void deleteById(UUID id) {
        if(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_AUTH)).getStartDate().isAfter(LocalDateTime.now()));
            repository.deleteById(id);
        throw new ResourceConflictException(ResponseErrorEnum.SAL002);
    }

    public Sale getById(UUID id) {
        return repository.findAllById(id);
    }
}
