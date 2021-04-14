package br.com.nzesportes.api.nzapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
public class AuthenticationResponse {
    private String token;
    private UUID id;
    private String username;
    private List<String> roles;
}
