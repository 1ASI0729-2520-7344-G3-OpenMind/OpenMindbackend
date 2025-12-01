package com.openmind.IAM.Infraestructure.Config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    private SecretKey getSignInKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(Long userId, String email){
        return Jwts.builder().subject(email).claim("userId", userId).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration)).signWith(getSignInKey()).compact();
    }

    public String extractEmail(String token){
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isValid(String token, String email){
        try{
            String tokenEmail = extractEmail(token);
            return email.equals(tokenEmail) && !isExpired(token);
        }catch(Exception e){
            return false;
        }
    }

    private boolean isExpired(String token){
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
}
