package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.product.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class SubCategorySaveTO {
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Boolean status;
    private List<UUID> categoriesToAdd;
    private List<UUID> categoriesToRemove;
}
