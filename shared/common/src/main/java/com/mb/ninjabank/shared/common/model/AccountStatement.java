package com.mb.ninjabank.shared.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatement extends ID implements BasicEntity{

    private String accountNumber;

    private BigDecimal amount;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountOperation accountOperation;

    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonProperty("modified_at")
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @Enumerated(EnumType.STRING)
    private StatementStatus statementStatus = StatementStatus.PENDING;


    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void setStatus(Status status) {
          this.status = status;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    @Override
    public LocalDateTime getModifiedDate() {
        return this.modifiedDate;
    }
}
