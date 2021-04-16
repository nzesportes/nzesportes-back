package br.com.nzesportes.api.nzapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {
    private static final long serialVersionUID = 2952659171804391823L;

    private ResponseErrorEnum error;
    public ResourceConflictException(ResponseErrorEnum error) {
        super(error.getText());
        this.error = error;
    }
}
