package br.com.nzesportes.api.nzapi.dtos;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private UUID id;
    private String username;
    private List<String> roles;
}
