package br.com.nzesportes.api.nzapi.dtos.bettersend;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Options {
    public double insurance_value;
    public boolean receipt;
    public boolean own_hand;
    public boolean reverse;
    public boolean non_commercial;
    public Invoice invoice;
    public String platform;
    public List<Tag> tags;
}
