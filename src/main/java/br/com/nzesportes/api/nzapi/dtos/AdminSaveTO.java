package br.com.nzesportes.api.nzapi.dtos;

import br.com.nzesportes.api.nzapi.domains.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AdminSaveTO {
    private UUID id;
    private String username;
    private Role role;
}
