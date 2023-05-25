package com.mb.ninjabank.httpclients.config;

import com.mb.ninjabank.httpclients.CustomerClient;
import com.mb.ninjabank.httpclients.decoder.CustomErrorDecoder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Configuration
@ComponentScan(basePackageClasses = {CustomerClient.class})
public class FeignConfiguration {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }

    @Bean
    public RequestInterceptor bearerTokenRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                if(Objects.nonNull(attributes)){
                    JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken)attributes.getRequest().getUserPrincipal();
                    Jwt token = authenticationToken.getToken();
                    String accessToken = token.getTokenValue();
                    template.header("Authorization",
                            String.format("Bearer %s", accessToken));
                }

            }
        };
    }

}
