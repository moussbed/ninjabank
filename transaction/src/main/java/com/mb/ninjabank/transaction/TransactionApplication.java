package com.mb.ninjabank.transaction;

import com.mb.ninjabank.transaction.config.TransactionConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(TransactionConfiguration.class)
public class TransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class,args);
    }
}
