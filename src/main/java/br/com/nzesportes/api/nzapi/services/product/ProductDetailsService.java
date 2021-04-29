package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.repositories.product.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductDetailsService {
    @Autowired
    private ProductDetailRepository repository;

    Boolean existsByBrandId(UUID brandId) {
        return repository.existsByBrandId(brandId);
    }
}
