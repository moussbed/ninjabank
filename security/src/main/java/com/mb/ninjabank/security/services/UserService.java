package com.mb.ninjabank.security.services;

import com.mb.ninjabank.security.entities.Role;
import com.mb.ninjabank.security.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

public interface UserService extends UserDetailsService {

    Role addRole(Role role);

    User addUser(User user);

    void addRolesToUser(String username, String ... roleNames);

    Collection<User> listUsers();
}
