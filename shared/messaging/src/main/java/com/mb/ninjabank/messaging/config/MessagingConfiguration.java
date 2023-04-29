package com.mb.ninjabank.messaging.config;

import com.mb.ninjabank.messaging.rabbit.RabbitMQConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RabbitMQConfiguration.class})
public class MessagingConfiguration {
}
