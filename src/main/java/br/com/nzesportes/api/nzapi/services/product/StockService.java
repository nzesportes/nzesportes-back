package br.com.nzesportes.api.nzapi.services.product;

import br.com.nzesportes.api.nzapi.domains.product.Stock;
import br.com.nzesportes.api.nzapi.dtos.product.UpdateStockTO;
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
        Stock stock = getById(dto.getId());
        stock.setQuantity(stock.getQuantity() + dto.getQuantityToAdd());
        return repository.save(stock);
    }


    public void deleteAll(List<UUID> stockToRemove) {
        repository.deleteAll(repository.findAllById(stockToRemove));
    }
}
