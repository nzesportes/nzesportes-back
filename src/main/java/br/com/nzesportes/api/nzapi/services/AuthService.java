package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.Role;
import br.com.nzesportes.api.nzapi.domains.User;
import br.com.nzesportes.api.nzapi.dtos.AuthenticationResponse;
import br.com.nzesportes.api.nzapi.dtos.AutheticationRequest;
import br.com.nzesportes.api.nzapi.errors.ResponseError;
import br.com.nzesportes.api.nzapi.repositories.UsersRepository;
import br.com.nzesportes.api.nzapi.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AuthService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsersRepository repository;

    public ResponseEntity<?> AuthenticateUser(AutheticationRequest autheticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(autheticationRequest.getUsername(), autheticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new AuthenticationResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    public ResponseEntity<?> registerUser(AutheticationRequest autheticationRequest) {
        if(repository.existsByUsername(autheticationRequest.getUsername()))
            ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseError.AUTH001);

        User user = new User(autheticationRequest.getUsername(),
                bCryptPasswordEncoder.encode(autheticationRequest.getPassword()), Role.USER);

        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário registrado");
    }
}
