package com.mb.ninjabank.security.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mb.ninjabank.shared.common.model.BasicEntity;
import com.mb.ninjabank.shared.common.model.ID;
import com.mb.ninjabank.shared.common.model.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "app_user")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends ID implements BasicEntity,UserDetails {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private Status status = Status.CREATED;

    @CreationTimestamp
    @JsonProperty("created_at")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @JsonProperty("modified_at")
    private LocalDateTime modifiedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
