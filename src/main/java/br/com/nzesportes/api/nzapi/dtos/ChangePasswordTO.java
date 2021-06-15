package br.com.nzesportes.api.nzapi.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordTO {
    private String currentPassword;
    private String newPassword;
}
