package com.example.modules.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CryptoUtils {

    private final String secretKey = "this-is-long-long-spring-react-secretKey-over-two-five-six-bits";

    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean comparePassword(String password, String hashed){
        return BCrypt.checkpw(password, hashed);
    }

    public String makeJwt(String email){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        int minute = 20;
        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 1000 * 60 * minute);

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();

        headerMap.put("type", "JWT");
        headerMap.put("alg", "HS256");

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public String checkJwt(String jwt) {
        try{
            Claims claims = Jwts
                    .parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(jwt)
                    .getBody();
            log.info("<<<<<< 토큰 정보 >>>>>>>" + claims);

            return claims.get("email").toString();
        }catch (ExpiredJwtException exception){
            log.info("토큰 만료");
            return null;
        }catch(JwtException exception){
            log.info("토큰 변조");
            return null;
        }
    }
}
