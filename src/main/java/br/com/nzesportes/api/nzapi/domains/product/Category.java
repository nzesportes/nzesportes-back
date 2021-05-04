package br.com.nzesportes.api.nzapi.domains.product;

import br.com.nzesportes.api.nzapi.converters.StringConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @Convert(converter = StringConverter.class)
    private List<String> type;
}
