package com.mb.ninjabank.shared.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class  Account extends ID implements BasicEntity{

    @JsonProperty("account_number")
    @NaturalId(mutable = false)
    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @JsonProperty("label")
    private String label;

    @JsonProperty("balance")
    private BigDecimal balance;

    @JsonProperty("account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

//    @OneToOne(mappedBy = "account",fetch = FetchType.LAZY)
//    private Customer customer;

    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonProperty("modified_at")
    private LocalDateTime modifiedDate;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

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
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
