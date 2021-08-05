package br.com.nzesportes.api.nzapi.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Setter
@Getter
@Table(name = "better_send")
public class BetterSend {
    @Id
    public String code;
    @JsonProperty("access_token")
    public String accessToken;
    @JsonProperty("token_type")
    public String tokenType;
    @JsonProperty("expires_in")
    public int expiresIn;
    @JsonProperty("refresh_token")
    public String refreshToken;
}
