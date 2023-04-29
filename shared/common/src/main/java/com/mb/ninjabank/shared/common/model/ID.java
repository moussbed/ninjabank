package com.mb.ninjabank.shared.common.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract class ID {

    @Id
    private UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }

}
