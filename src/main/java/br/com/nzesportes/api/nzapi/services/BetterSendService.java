package br.com.nzesportes.api.nzapi.services;

import br.com.nzesportes.api.nzapi.domains.BetterSend;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<BetterSend> tokens = repository.findAll();
            if(tokens.size() > 0){
                TOKEN = tokens.get(tokens.size()-1).accessToken;
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
}
