package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.BetterSend;
import br.com.nzesportes.api.nzapi.dtos.BetterSendTokenStatusTO;
import br.com.nzesportes.api.nzapi.errors.ResourceUnauthorizedException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.BetterSendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@EnableScheduling
@Slf4j
public class BetterSendService {
    private static String TOKEN;
    @Value("${nz.melhor-envio.url}")
    private String URI;

    @Value("${nz.melhor-envio.client-id}")
    private String CLIENT_ID;

    @Value("${nz.melhor-envio.client-secret}")
    private String CLIENT_SECRET;

    @Value("${nz.melhor-envio.user-agent}")
    private String USER_AGENT;

    @Value("${nz.melhor-envio.redirect-uri}")
    private String REDIRECT_URI;

    @Autowired
    private BetterSendRepository repository;

    public ResponseEntity<?> postToken(String code){

        // request headers parameters
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", USER_AGENT);

        // request body parameters
        Map<String, Object> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("client_id", CLIENT_ID);
        body.put("client_secret", CLIENT_SECRET);
        body.put("redirect_uri", REDIRECT_URI);
        body.put("code", code);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        RestTemplate rest = new RestTemplate();
        log.info("melhor envio URI: ", URI);

        // send POST request
        ResponseEntity<BetterSend> response = rest.postForEntity(URI + "oauth/token", entity, BetterSend.class);


        if(response.getStatusCode() == HttpStatus.OK){
            TOKEN = response.getBody().accessToken;
            response.getBody().setCode(code);
            repository.save(response.getBody());
            return ResponseEntity.ok(null);
        }
        throw new ResourceUnauthorizedException(ResponseErrorEnum.NOT_AUTH);
    }

    public ResponseEntity<?> calculateShipping(Object bodyRequest){

        if(TOKEN == null){
            List<BetterSend> tokens = repository.findTop10ByOrderByCreationDateDesc();
            if(tokens.size() > 0){
                TOKEN = tokens.get(0).accessToken;
            }else {
                throw new ResourceUnauthorizedException(ResponseErrorEnum.NOT_AUTH);
            }
        }
        // request headers parameters
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        headers.add("User-Agent", USER_AGENT);
        headers.add("Authorization", "Bearer " + TOKEN);


        RestTemplate rest = new RestTemplate();

        HttpEntity<Object> entity = new HttpEntity<>(bodyRequest, headers);

        try {
            ResponseEntity<Object> response = rest.postForEntity(URI + "api/v2/me/shipment/calculate", entity, Object.class);

            if(response.getStatusCode() == HttpStatus.OK){
                return response;
            }
        }catch (Exception ex ){
            System.out.println(ex.toString());
        }

        throw new ResourceUnauthorizedException(ResponseErrorEnum.NOT_AUTH);
    }

    public ResponseEntity<?> isValidToken() {
        List<BetterSend> tokens = repository.findTop10ByOrderByCreationDateDesc();

        BetterSendTokenStatusTO betterSendToken= new BetterSendTokenStatusTO();

        if(tokens.size() > 0){
            BetterSend token = tokens.get(0);

            Calendar creationDate30 = Calendar.getInstance();
            creationDate30.setTime(token.getCreationDate());
            creationDate30.add(Calendar.DAY_OF_MONTH, 30);


            Calendar creationDate45 = Calendar.getInstance();
            creationDate45.setTime(token.getCreationDate());
            creationDate45.add(Calendar.DAY_OF_MONTH, 45);

            Date actualDate = Calendar.getInstance().getTime();

            //valid
            if(!actualDate.after(creationDate30.getTime())){
                betterSendToken.setStatus(BetterSendTokenStatusTO.Status.VALID);
            }
            // expired
            if(actualDate.after(creationDate30.getTime()) && actualDate.before(creationDate45.getTime())){
                betterSendToken.setStatus(BetterSendTokenStatusTO.Status.EXPIRED);
            }
            // invalid
            if(actualDate.after(creationDate45.getTime())){
                betterSendToken.setStatus(BetterSendTokenStatusTO.Status.INVALID);
            }
            return ResponseEntity.ok(betterSendToken);

        }
        // UNCREATED
        betterSendToken.setStatus(BetterSendTokenStatusTO.Status.UNCREATED);
        return ResponseEntity.ok(betterSendToken);
    }


    public String refreshToken(){
        List<BetterSend> tokens = repository.findTop10ByOrderByCreationDateDesc();

        // request headers parameters
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", USER_AGENT);

        // request body parameters
        Map<String, Object> body = new HashMap<>();
        body.put("grant_type", "refresh_token");
        body.put("client_id", CLIENT_ID);
        body.put("client_secret", CLIENT_SECRET);
        body.put("refresh_token", tokens.get(0).refreshToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        RestTemplate rest = new RestTemplate();
        // send POST request
        ResponseEntity<BetterSend> response = rest.postForEntity(URI + "oauth/token", entity, BetterSend.class);


        if(response.getStatusCode() == HttpStatus.OK){
            TOKEN = response.getBody().accessToken;
            repository.save(response.getBody());
            return response.getBody().accessToken;
        }
        throw new ResourceUnauthorizedException(ResponseErrorEnum.NOT_AUTH);
    }

    @Scheduled(cron = "30 35 23 * * *")
    public void scheduledRefreshToken() {
        List<BetterSend> tokens = repository.findTop10ByOrderByCreationDateDesc();

        if(tokens.size() > 0) {

            Date actualDate = Calendar.getInstance().getTime();

            BetterSend token = tokens.get(0);

            Calendar creationDate28 = Calendar.getInstance();
            creationDate28.setTime(token.getCreationDate());
            creationDate28.add(Calendar.DAY_OF_MONTH, 28);

            if(actualDate.after(creationDate28.getTime())){
                System.out.println("Invalid token, refreshing token..." + Calendar.getInstance().getTime());
                this.refreshToken();
            }else{
                System.out.println("valid better send Token verification" + Calendar.getInstance().getTime());
            }
        }
        System.out.println("Token better send not found");
    }

}
