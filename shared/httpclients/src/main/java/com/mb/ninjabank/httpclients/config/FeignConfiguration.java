package com.mb.ninjabank.httpclients.config;

import com.mb.ninjabank.httpclients.CustomerClient;
import com.mb.ninjabank.httpclients.decoder.CustomErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {CustomerClient.class})
public class FeignConfiguration {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new CustomErrorDecoder();
    }

}
