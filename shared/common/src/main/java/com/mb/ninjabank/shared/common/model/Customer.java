package com.mb.ninjabank.shared.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Entity
public class Customer extends ID implements BasicEntity {

    private String firstName;
    private String lastName;

    @OneToOne
    private Account account;
    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdDate;
    @UpdateTimestamp
    @JsonProperty("modified_at")
    private LocalDateTime modifiedDate;

    private Status status = Status.CREATED;

    private String email;


    public Customer() {
    }


    public Customer( String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Status getStatus() {
        return this.status;
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
