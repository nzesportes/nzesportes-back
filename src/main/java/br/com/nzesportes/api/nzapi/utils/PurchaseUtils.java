package br.com.nzesportes.api.nzapi.utils;

import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.dtos.purchase.PurchaseTO;
import br.com.nzesportes.api.nzapi.services.payment.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import static org.springframework.beans.BeanUtils.copyProperties;

@Service
public class PurchaseUtils {
    @Autowired
    private PurchaseService purchaseService;

    public Page<PurchaseTO> toPurchasePage(Page<Purchase> purchases) {
        return purchases.map(this::toPurchaseTO);
    }

    public PurchaseTO toPurchaseTO(Purchase purchase) {
        PurchaseTO dto = new PurchaseTO();
        copyProperties(purchase, dto);

        return dto;
    }
}
