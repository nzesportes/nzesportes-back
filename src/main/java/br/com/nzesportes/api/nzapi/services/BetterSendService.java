package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.BetterSend;
import br.com.nzesportes.api.nzapi.dtos.BetterSendTokenStatusTO;
import br.com.nzesportes.api.nzapi.errors.ResourceUnauthorizedException;
import br.com.nzesportes.api.nzapi.errors.ResponseErrorEnum;
import br.com.nzesportes.api.nzapi.repositories.BetterSendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class BetterSendService {
    private static final String URI = "https://sandbox.melhorenvio.com.br/";
    private static String TOKEN;

    @Autowired
    private BetterSendRepository repository;

    public ResponseEntity<?> postToken(String code){

        // request headers parameters
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "nzesportes-dev (andrelive.05@hotmail.com");

        // request body parameters
        Map<String, Object> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("client_id", "2040");
        body.put("client_secret", "jbiIaXntsiQ0slcrl8XowPybEQtV1KT4uXqMfmeb");
        body.put("redirect_uri", "https://nzesportes-dev-front.herokuapp.com/painel/melhor-envio/codigo");
        body.put("code", code);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        RestTemplate rest = new RestTemplate();
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
        headers.add("User-Agent", "nzesportes-dev (andrelive.05@hotmail.com");
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
        headers.add("User-Agent", "nzesportes-dev (andrelive.05@hotmail.com");

        // request body parameters
        Map<String, Object> body = new HashMap<>();
        body.put("grant_type", "refresh_token");
        body.put("client_id", "2040");
        body.put("client_secret", "jbiIaXntsiQ0slcrl8XowPybEQtV1KT4uXqMfmeb");
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
}
