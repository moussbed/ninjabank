package com.mb.ninjabank.security.services.impl;

import com.mb.ninjabank.security.entities.Role;
import com.mb.ninjabank.security.entities.User;
import com.mb.ninjabank.security.repositories.RoleRepository;
import com.mb.ninjabank.security.repositories.UserRepository;
import com.mb.ninjabank.security.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final RoleRepository  roleRepository;

    private  final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).get(0);
    }

    @Override
    public Role addRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User addUser(User user) {
        final String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void addRolesToUser(String username, String... roleNames) {
        User user = userRepository.findByUsername(username).get(0);
        List<Role> roles = Stream.of(roleNames).map(r-> roleRepository.findByRoleName(r).get(0)).toList();
        user.getRoles().addAll(roles);
    }

    @Override
    public Collection<User> listUsers() {
        return userRepository.findAll();
    }
}
