package com.example.youreview.Configurations.Security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.youreview.Configurations.Utils.CONSTANTS;
import com.example.youreview.Services.Impl.UserServiceImpl;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class securityConfiguration {
    @Value("${app.security.jwt.keystore-location}")
    private String KeyStorePath;
    @Value("${app.security.jwt.keystore-password}")
    private String KeyStorePassword;
    @Value("${app.security.jwt.keystore-alias}")
    private String KeyStoreAlias;
    @Value("${app.security.jwt.private-key-passPhrase}")
    private String passPhrase;
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    public securityConfiguration(UserServiceImpl userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @Bean
     protected SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        System.out.println("inside filter");
        http.authorizeHttpRequests(req -> {
                req.requestMatchers(new AntPathRequestMatcher("/api/auth/signin", HttpMethod.POST.name())).permitAll()
                .requestMatchers("/api/auth/signin").permitAll().anyRequest().authenticated();
            })
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(getJwtAuthenticationConverter())
                )
            );
        return http.build();
    }

    private JwtAuthenticationConverter getJwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter authorityConverter = new JwtGrantedAuthoritiesConverter();
        authorityConverter.setAuthorityPrefix(CONSTANTS.AUTHORITY_PREFIX);
        authorityConverter.setAuthoritiesClaimName(CONSTANTS.ROLE_CLAIM);
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorityConverter);
        return converter;
    }

    @Bean
    public KeyStore keyStore(){
        try{
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resStream = Thread.currentThread().getContextClassLoader().getResourceAsStream (KeyStorePath);
            keyStore.load(resStream, KeyStorePassword.toCharArray());
            return keyStore;
        }catch(IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e){
            System.out.printf("Unable to load keystore: {} %s %s", KeyStorePath, e);
        }
        throw new IllegalArgumentException("Unable to load keystore");
    }

    @Bean
    public RSAPrivateKey jwtSigningKey(KeyStore keyStore){
        try{
            Key key = keyStore.getKey(KeyStoreAlias, passPhrase.toCharArray());
            if(key instanceof RSAPrivateKey)
                return (RSAPrivateKey) key;
        }catch (UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e) {
            System.out.printf("key from keystore: {} %s %s", KeyStorePath, e);
        }
        throw new IllegalArgumentException("Cant loadprivate key");
    }

    @Bean
    public RSAPublicKey jwtValidationKey(KeyStore keyStore){
        try{
            Certificate certificate = keyStore.getCertificate(KeyStoreAlias);
            PublicKey publicKey = certificate.getPublicKey();
            if(publicKey instanceof RSAPublicKey)
                return (RSAPublicKey) publicKey;
        }catch (KeyStoreException e) {
            System.out.printf("key from keystore: {} %s %s", KeyStorePath, e);
        }
        throw new IllegalArgumentException("Cant loadprivate key");
    }

    @Bean
    public JwtDecoder jwtDecoder(RSAPublicKey publicKey){
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }
}