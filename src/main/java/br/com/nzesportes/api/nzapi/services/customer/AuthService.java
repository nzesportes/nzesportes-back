package br.com.nzesportes.api.nzapi.services.customer;

import br.com.nzesportes.api.nzapi.domains.customer.RecoveryRequest;
import br.com.nzesportes.api.nzapi.domains.customer.RecoveryType;
import br.com.nzesportes.api.nzapi.domains.customer.Role;
import br.com.nzesportes.api.nzapi.domains.customer.User;
import br.com.nzesportes.api.nzapi.dtos.AbstractResponse;
import br.com.nzesportes.api.nzapi.dtos.customer.AuthenticationRequest;
import br.com.nzesportes.api.nzapi.dtos.customer.AuthenticationResponse;
import br.com.nzesportes.api.nzapi.dtos.customer.ChangePasswordTO;
import br.com.nzesportes.api.nzapi.dtos.customer.RecoveryTO;
import br.com.nzesportes.api.nzapi.errors.ResourceConflictException;
import br.com.nzesportes.api.nzapi.errors.ResourceNotFoundException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.RecoveryRequestRepository;
import br.com.nzesportes.api.nzapi.repositories.customer.UsersRepository;
import br.com.nzesportes.api.nzapi.security.jwt.JwtUtils;
import br.com.nzesportes.api.nzapi.security.services.UserDetailsImpl;
import br.com.nzesportes.api.nzapi.services.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AuthService {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private final static String FIRST_ACCESS = "first-access";
    private final static String PASSWORD_RECOVERY = "recovery";

    @Value("${nz.recovery.url}")
    private String recoveryUrl;
    @Value("${nz.first-access.url}")
    private String firstAccessUrl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RecoveryRequestRepository recoveryRequestRepository;

    @Autowired
    private EmailService mailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UsersRepository repository;

    public AuthenticationResponse authenticateUser(AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());

        return new AuthenticationResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles);
    }

    public ResponseEntity<?> registerUser(AuthenticationRequest authenticationRequest) {
        if(repository.existsByUsername(authenticationRequest.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponseErrorEnum.AUTH001.getText());

        User user = new User(authenticationRequest.getUsername(),
                bCryptPasswordEncoder.encode(authenticationRequest.getPassword()), Role.ROLE_USER);

        repository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authenticateUser(authenticationRequest));
    }

    public ResponseEntity<?> changePassword(ChangePasswordTO dto, UserDetailsImpl principal) {
        User user = repository.findById(principal.getId()).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        if(bCryptPasswordEncoder.matches(dto.getCurrentPassword(), user.getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
            repository.save(user);
            return  ResponseEntity.ok(authenticateUser(new AuthenticationRequest(principal.getUsername(), dto.getNewPassword())));
        }
        return ResponseEntity.status(409).body(new AbstractResponse("Senha atual inválida"));
    }

    public ResponseEntity<?> createFlow(AuthenticationRequest authenticationRequest, String flow) {
        User user = repository.findByUsername(authenticationRequest.getUsername()).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        RecoveryRequest request = recoveryRequestRepository.findByUserIdAndType(user.getId(), RecoveryType.FIRST_ACCESS);
        String url;

        if(request != null && request.getStatus())
            throw new ResourceNotFoundException(ResponseErrorEnum.COMPLETED);
        else if (request != null && (request.getStatus() == null || request.getStatus() == false)) {
            url = request.getType().equals(RecoveryType.PASSWORD_RECOVERY) ? recoveryUrl : firstAccessUrl;
            this.mailService.sendEmailAuth(
                    user.getUsername(),
                    url + request.getId(),
                    request.getType().equals(RecoveryType.PASSWORD_RECOVERY) ? true : false
            );
            return ResponseEntity.ok(new RecoveryTO(request.getId()));
        }
        request = new RecoveryRequest();
        if (FIRST_ACCESS.equals(flow)) {
            request.setType(RecoveryType.FIRST_ACCESS);
            url = firstAccessUrl;
        }   else if (PASSWORD_RECOVERY.equals(flow)) {
            request.setType(RecoveryType.PASSWORD_RECOVERY);
            url = recoveryUrl;
        }
        else
            throw new ResourceConflictException(ResponseErrorEnum.NOT_AUTH);

        request.setUserId(user.getId());
        request.setStatus(false);

        request = recoveryRequestRepository.save(request);


        this.mailService.sendEmailAuth(
                user.getUsername(),
                url + request.getId(),
                request.getType().equals(RecoveryType.PASSWORD_RECOVERY) ? true : false
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new AbstractResponse("created"));
    }


    public ResponseEntity<AuthenticationResponse> performFlow(UUID id, ChangePasswordTO dto, String flow) {
        if(!(FIRST_ACCESS.equals(flow) || PASSWORD_RECOVERY.equals(flow)))
            throw new ResourceConflictException(ResponseErrorEnum.NOT_AUTH);

        RecoveryRequest request = recoveryRequestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        if(request.getStatus() == true)
            throw new ResourceConflictException(ResponseErrorEnum.COMPLETED);

        User user = repository.findById(request.getUserId()).orElseThrow(() -> new ResourceNotFoundException(ResponseErrorEnum.NOT_FOUND));
        user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
        user = repository.save(user);

        request.setStatus(true);
        recoveryRequestRepository.save(request);

        return ResponseEntity.ok(authenticateUser(new AuthenticationRequest(user.getUsername(), dto.getNewPassword())));
    }
}
