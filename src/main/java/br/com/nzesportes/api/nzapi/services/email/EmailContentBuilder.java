package br.com.nzesportes.api.nzapi.services.email;

import br.com.nzesportes.api.nzapi.domains.purchase.Purchase;
import br.com.nzesportes.api.nzapi.domains.purchase.PurchaseItems;
import br.com.nzesportes.api.nzapi.dtos.product.ProductDetailsTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;

@Service
public class EmailContentBuilder {
    private TemplateEngine templateEngine;

    @Autowired
    public EmailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String text, String title, String action, String link, List<ProductDetailsTO> products, Purchase purchase) {
        Context context  = new Context();
        context.setVariable("text", text);
        context.setVariable("title", title);
        context.setVariable("action", action);
        context.setVariable("link", link);
        context.setVariable("products", products);
        context.setVariable("purchase", purchase);

        return templateEngine.process("template-email", context);
    }

}
