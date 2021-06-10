package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.dtos.ProductDetailUpdateTO;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ProductDetailsService {
    @Autowired
    private ProductDetailRepository repository;

    public ProductDetails getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.PDT001));
    }

    public ProductDetails update(ProductDetailUpdateTO dto) {
        ProductDetails details = getById(dto.getId());
        copyProperties(dto, details);
        return repository.save(details);
    }

    public ProductDetails save(ProductDetails details) {
        return repository.save(details);
    }

    public List<ProductDetails> saveAll(List<ProductDetails> details) {
        return repository.saveAll(details);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
