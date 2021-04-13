package br.com.nzesportes.api.nzapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutheticationRequest {
    private String username;
    private String password;
}
