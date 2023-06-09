package com.mb.ninjabank.backend;

import com.mb.ninjabank.backend.config.BackendConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({BackendConfiguration.class})
public class BackendApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(BackendApplication.class,args);

    }
}
