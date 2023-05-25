package com.mb.ninjabank.backend.config;

import com.mb.ninjabank.backend.BackendApplication;
import com.mb.ninjabank.backend.repositories.NaturalRepositoryImpl;
import com.mb.ninjabank.shared.common.config.CommonConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Import({CommonConfiguration.class})
@ComponentScan(basePackageClasses = {BackendApplication.class})
@EnableJpaRepositories(basePackages = {"com.mb.ninjabank.backend.repositories"}, repositoryBaseClass = NaturalRepositoryImpl.class)
@EntityScan(basePackages = "com.mb.ninjabank.shared.common.model")
@EnableConfigurationProperties(RsaKeyConfiguration.class)
public class BackendConfiguration {
}
