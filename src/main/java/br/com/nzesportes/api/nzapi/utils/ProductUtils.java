package br.com.nzesportes.api.nzapi.utils;

import br.com.nzesportes.api.nzapi.domains.product.Product;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.dtos.product.ProductDetailsTO;
import br.com.nzesportes.api.nzapi.dtos.product.ProductTO;
import br.com.nzesportes.api.nzapi.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class ProductUtils {
    @Autowired
    private ProductService productService;


    public Page<ProductDetailsTO> toProductDetailsPage(Page<ProductDetails> productDetails) {
        return productDetails.map(this::toProductDetailsTO);
    }

    public ProductDetailsTO toProductDetailsTO(ProductDetails details) {
        ProductDetailsTO dto = new ProductDetailsTO();
        copyProperties(details, dto);

        details.getSales().parallelStream()
                .filter(sale -> sale.getStartDate().isBefore(LocalDateTime.now())
                        && sale.getEndDate().isAfter(LocalDateTime.now())).findFirst().ifPresent(sale -> dto.setSale(sale));

        dto.setProduct(toProductTO(productService.getById(details.getProductId())));
        return dto;
    }

    public ProductTO toProductTO(Product product) {
        ProductTO dto = new ProductTO();
        copyProperties(product, dto);
        return dto;
    }
}
