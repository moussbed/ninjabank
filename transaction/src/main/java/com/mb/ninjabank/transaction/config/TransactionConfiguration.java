package com.mb.ninjabank.transaction.config;

import com.mb.ninjabank.httpclients.CustomerClient;
import com.mb.ninjabank.httpclients.config.FeignConfiguration;
import com.mb.ninjabank.messaging.config.MessagingConfiguration;
import com.mb.ninjabank.transaction.TransactionApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableConfigurationProperties({ConfigStatement.class, RsaKeyConfiguration.class})
@ComponentScan(basePackageClasses= {TransactionApplication.class})
@EnableJpaRepositories(basePackages = {"com.mb.ninjabank.transaction.repositories"})
@EntityScan(basePackages = "com.mb.ninjabank.shared.common.model")
@EnableFeignClients(basePackageClasses = CustomerClient.class)
@EnableScheduling
@Import({FeignConfiguration.class,MessagingConfiguration.class})
public class TransactionConfiguration {
}
