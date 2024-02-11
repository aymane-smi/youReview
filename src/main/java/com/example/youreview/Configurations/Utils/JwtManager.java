package com.example.youreview.Configurations.Utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Date;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtManager {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    public JwtManager(
        @Lazy RSAPrivateKey privateKey,
        @Lazy RSAPublicKey publicKey
    ){
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String CreateToken(UserDetails principal){
        final long now = System.currentTimeMillis();
        return JWT.create()
                    .withIssuer("issuer")
                    .withSubject(principal.getUsername())
                    .withClaim(
                        CONSTANTS.ROLE_CLAIM,
                        principal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                    .withIssuedAt(new Date(now))
                    .withExpiresAt(new Date(now+CONSTANTS.EXPIRATION_TIME))
                    .sign(Algorithm.RSA256(publicKey, privateKey));
    }
}
