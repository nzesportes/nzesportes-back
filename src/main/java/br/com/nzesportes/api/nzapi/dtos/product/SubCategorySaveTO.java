package br.com.nzesportes.api.nzapi.dtos.product;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Getter
@Setter
public class SubCategorySaveTO {
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean status;
    private UUID categoryId;
    private Boolean onMenu;
}
