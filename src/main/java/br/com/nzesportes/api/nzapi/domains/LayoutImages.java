package br.com.nzesportes.api.nzapi.domains;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@RedisHash("LayoutImages")
public class LayoutImages implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String slideImages;
    private String bannerImages;
}
