package br.com.nzesportes.api.nzapi.dtos.mercadopago.preference;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PreferenceSearchPage {
    private List<PreferenceSearch> elements;
    private int next_offset;
    private int total;
}
