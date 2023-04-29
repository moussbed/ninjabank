package com.mb.ninjabank.shared.common.model;

import java.time.LocalDateTime;

public interface BasicEntity {

     Status getStatus();
     void setStatus(Status status);
     LocalDateTime getCreatedDate();
     LocalDateTime getModifiedDate();
}
