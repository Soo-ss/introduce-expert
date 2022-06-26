package com.api.backend.modules.kakao;

import com.api.backend.infra.advice.exception.COAuthException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class KakaoService {

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    public KakaoProfile getKakaoProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        // TODO: Set http entity
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);
        try{
            // TODO: Request profile
            ResponseEntity<String> response = restTemplate.postForEntity(
                    env.getProperty("spring.social.kakao.url.profile"),
                    request,
                    String.class);

            if(response.getStatusCode() == HttpStatus.OK){
                return gson.fromJson(response.getBody(), KakaoProfile.class);
            }
        }catch (Exception e){
            throw new COAuthException();
        }
        throw new COAuthException();
    }
}
