package com.mb.ninjabank.transaction.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("cronjob.statement")
public class ConfigStatement {

    private long duration;

    private String temporalUnit;

    private String expression;

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getTemporalUnit() {
        return temporalUnit;
    }

    public void setTemporalUnit(String temporalUnit) {
        this.temporalUnit = temporalUnit;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}