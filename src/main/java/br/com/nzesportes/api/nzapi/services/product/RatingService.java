package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.customer.Customer;
import br.com.nzesportes.api.nzapi.domains.product.Rating;
import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.dtos.product.RatingSaveTO;
import br.com.nzesportes.api.nzapi.dtos.product.RatingUpdateTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.RatingRepository;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.customer.CustomerService;
import br.com.nzesportes.api.nzapi.services.payment.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class RatingService {
    @Autowired
    private RatingRepository repository;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private CustomerService customerService;

    public Rating save(RatingSaveTO dto, UserDetailsImpl principal) {
        Purchase purchase = purchaseService.getByIdAndCustomerId(dto.getPurchaseId(), customerService.getByUserId(principal.getId()).getId());
        repository.findByPurchaseId(purchase.getId()).ifPresent(rating -> { throw new ResourceConflictException(ResponseErrorEnum.COMPLETED); });

        if(purchase.getItems().parallelStream().anyMatch(purchaseItems -> purchaseItems.getItem().getProductDetail().getProductId().equals(dto.getProductId())))
            return repository.save(Rating.builder()
                    .rate(dto.getRate())
                    .title(dto.getTitle())
                    .comment(dto.getComment())
                    .productId(dto.getProductId())
                    .purchaseId(purchase.getId())
                    .customer(purchase.getCustomer()).build());

        throw new ResourceConflictException(ResponseErrorEnum.RAT002);
    }

    public Rating getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
    }

    public Page<Rating> getAll(int page, int size, UserDetailsImpl principal) { return repository.findByCustomerIdOrderByCreationDateDesc(customerService.getByUserId(principal.getId()).getId(), PageRequest.of(page, size)); }

    public Page<Rating> getAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

    public Page<Rating> getAllByProductId(UUID id, int page, int size) { return repository.findByProductId(id, PageRequest.of(page, size)); }

    public Rating update(RatingUpdateTO dto, UserDetailsImpl principal) {
        Rating rating = getById(dto.getId());
        if(rating.getCustomer().getUserId().equals(principal.getId())) {
            copyProperties(dto, rating);
            return repository.save(rating);
        }
        throw new ResourceConflictException(ResponseErrorEnum.CONFLICTED_OPERATION);
    }

    public void deleteById(UUID id, UserDetailsImpl principal) {
        Rating rating = getById(id);
        Customer customer = customerService.getByUserId(principal.getId());

        if(customer.getId().equals(rating.getCustomer().getId()))
            repository.deleteById(id);

        throw new ResourceConflictException(ResponseErrorEnum.CONFLICTED_OPERATION);
    }

    public Page<Rating> getAllByPurchaseId(UUID id, int page, int size) {
        return repository.findByProductId(id, PageRequest.of(page, size));
    }
}
