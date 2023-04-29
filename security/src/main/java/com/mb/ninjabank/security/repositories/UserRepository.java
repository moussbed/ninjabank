package com.mb.ninjabank.security.repositories;

import com.mb.ninjabank.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByUsername(String username);
}
