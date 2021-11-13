package br.com.nzesportes.api.nzapi.services.melhorenvio;

import br.com.nzesportes.api.nzapi.domains.product.Product;
import br.com.nzesportes.api.nzapi.domains.product.Size;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.melhorenvio.SizeRepository;
import br.com.nzesportes.api.nzapi.repositories.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SizeService {
    @Autowired
    private SizeRepository repository;

    @Autowired
    private ProductRepository productRepository;

    public Size save(Size size) {
        return repository.save(size);
    }

    public Size getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_AUTH));
    }

    public Page<Size> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public void deleteById(UUID id) {
        List<Product> products = productRepository.findBySizeId(id);
        if(products != null && products.size() > 0)
            throw new ResourceConflictException(ResponseErrorEnum.CONFLICTED_OPERATION);
        repository.deleteById(id);
    }

    public Size update(Size size) {
        return repository.save(size);
    }
}
