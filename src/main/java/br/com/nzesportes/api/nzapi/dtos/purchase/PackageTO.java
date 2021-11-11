package br.com.nzesportes.api.nzapi.dtos.purchase;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PackageTO {
    public String price;
    public String discount;
    public String format;
    public DimensionsTO dimensions;
    public String weight;
    public String insurance_value;
    public List<ProductPackageTO> products;
}
