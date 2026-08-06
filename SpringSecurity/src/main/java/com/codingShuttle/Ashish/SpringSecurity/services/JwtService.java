package com.codingShuttle.Ashish.SpringSecurity.services;

import com.codingShuttle.Ashish.SpringSecurity.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
    public String generateAccessToken(User user){
        return Jwts.builder() // we are telling in advance that we are trying to build a token.
                .subject(user.getId().toString()) // helps to uniquely identify a token for developers.
                .claim("email", user.getEmail()) // these are simply (key, value) pairs parts of the payload.
                .claim("roles", Set.of("ADMIN", "USER"))
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*10)) // It is preferred to set the expiration time.
                .signWith(getSecretKey()) // Remember we can't directly sign with our String type of Secret key. It requires the object of SecretKey.
                .compact();

    }

    public String generateRefreshToken(User user){
        return Jwts.builder() // we are telling in advance that we are trying to build a token.
                .subject(user.getId().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L*60*60*24*30*6)) // 6-month expiration of refresh token.
                .signWith(getSecretKey())
                .compact();

    }

    public Long getUserIdFromToken(String token){
        Claims claims = Jwts.parser() // we are telling in advance that we are parsing a token.
                .verifyWith(getSecretKey())// verify token if someone has tempered it or not. Remember it not completely verifies the token. It simply set the key inside the builder.
                .build() // build the parser object .
                .parseSignedClaims(token) // reads/decodes the JWT and even verifies expiration. It breaks it in the basis of ".". This actually makes the token parsable. Before it is was not in a parsable form.
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }
}
