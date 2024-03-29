package br.com.nzesportes.api.nzapi.utils;

import br.com.nzesportes.api.nzapi.domains.product.Product;
import br.com.nzesportes.api.nzapi.domains.product.ProductDetails;
import br.com.nzesportes.api.nzapi.domains.product.Sale;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.dtos.product.ProductDetailsTO;
import br.com.nzesportes.api.nzapi.dtos.product.ProductTO;
import br.com.nzesportes.api.nzapi.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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

        Optional<Sale> sale =  getSale(details);
        if(sale.isPresent())
            dto.setSale(sale.get());

        dto.setProduct(toProductTO(productService.getById(details.getProductId())));
        return dto;
    }

    public Optional<Sale> getSale(ProductDetails details) {
        return details.getSales().parallelStream()
                .filter(sale -> !sale.getStartDate().isAfter(LocalDate.now())
                        && !sale.getEndDate().isBefore(LocalDate.now())
                        && sale.getStatus()).findFirst();
    }

    public List<ProductDetailsTO> toProductDetailsList(List<ProductDetails> products) {
       return products.parallelStream().map(this::toProductDetailsTO).collect(Collectors.toList());
    }

    public ProductTO toProductTO(Product product) {
        ProductTO dto = new ProductTO();
        copyProperties(product, dto);
        return dto;
    }

    public List<ProductDetailsTO> toPurchaseProductDetailsList(List<PurchaseItems> items) {
        return items.parallelStream().map(this::toPurchaseDetailsTO).collect(Collectors.toList());
    }

    public ProductDetailsTO toPurchaseDetailsTO(PurchaseItems i) {
        ProductDetailsTO details = toProductDetailsTO(i.getItem().getProductDetail());
        details.setPurchaseStockId(i.getItem().getId());
        return details;
    }
}
