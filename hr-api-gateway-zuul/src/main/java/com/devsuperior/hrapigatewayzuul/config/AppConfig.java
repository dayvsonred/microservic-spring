package com.devsuperior.hrapigatewayzuul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class AppConfig {
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
        //tokenConverter.setSigningKey(jwtSecret);
        /** The biggest secret key here
         * never leave it in the code
         * **/
        tokenConverter.setSigningKey("zV$_(J~]U#$A;xRyRV+GFRS{<Ysmu7Ug2;2UM(>95^PG<(yrvsVZq5xA24f2&c@y");
        return tokenConverter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }
}
