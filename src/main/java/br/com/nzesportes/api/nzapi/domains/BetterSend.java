package br.com.nzesportes.api.nzapi.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Calendar;
import java.util.Date;

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
    public Date creationDate;

    @PrePersist
    public void prePersist() {
        creationDate = Calendar.getInstance().getTime();
    }
}
