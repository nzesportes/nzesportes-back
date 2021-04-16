package br.com.nzesportes.api.nzapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2952659171804391823L;

    private ResponseErrorEnum error;

    public ResourceNotFoundException(ResponseErrorEnum error) {
        super(error.getText());
        this.error = error;
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
