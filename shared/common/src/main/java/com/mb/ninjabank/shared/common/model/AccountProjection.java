package com.mb.ninjabank.shared.common.model;

import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "mobile", types = Account.class)
public interface AccountProjection {

    String getId();
    String getLabel();
    BigDecimal getBalance();

}
