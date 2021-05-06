package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Product;
import br.com.nzesportes.api.nzapi.dtos.StatusTO;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public Product getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRD001));
    }

    public Page<Product> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Product update(Product product) {
        return repository.save(product);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public StatusTO changeStatus(UUID id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PRD001));
        product.setStatus(!product.getStatus());
        repository.save(product);
        return new StatusTO(product.getStatus());
    }
}
