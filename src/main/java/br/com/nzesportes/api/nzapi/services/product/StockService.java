package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import br.com.nzesportes.api.nzapi.dtos.product.UpdateStockTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.product.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StockService {
    @Autowired
    private StockRepository repository;

    public List<Stock> saveAll(List<Stock> stocks) {
        return repository.saveAll(stocks);
    }

    public Stock getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.STK001));
    }

    public Stock updateQuantity(UpdateStockTO dto) {
        Stock response = repository.updateQuantity(dto.getId(), dto.getQuantityToAdd());
        if(response != null) {
            if (response.getQuantity() >= 0)
                return response;
            repository.updateQuantity(dto.getId(), dto.getQuantityToAdd() * -1);
        }
        repository.commit();
        throw new ResourceConflictException(ResponseErrorEnum.STK002);
    }



    public void deleteAll(List<UUID> stockToRemove) {
        repository.deleteAll(repository.findAllById(stockToRemove));
    }

    public Stock updateStatus(UpdateStockTO dto) {
        return repository.updateStatus(dto.getId());
    }
}
