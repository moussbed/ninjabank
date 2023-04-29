package com.mb.ninjabank.security.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mb.ninjabank.shared.common.model.BasicEntity;
import com.mb.ninjabank.shared.common.model.ID;
import com.mb.ninjabank.shared.common.model.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "app_role")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role extends ID implements BasicEntity {

    @Column(unique = true)
    private String roleName;

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonProperty("modified_at")
    private LocalDateTime modifiedDate;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Role)) return false;
//        Role role = (Role) o;
//        return Objects.equals(roleName, role.roleName) && status == role.status && Objects.equals(createdDate, role.createdDate) && Objects.equals(modifiedDate, role.modifiedDate);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(roleName, status, createdDate, modifiedDate);
//    }
}
