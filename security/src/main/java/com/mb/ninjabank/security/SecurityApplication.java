package com.mb.ninjabank.security;

import com.mb.ninjabank.security.config.SecurityConfiguration;
import com.mb.ninjabank.security.entities.Role;
import com.mb.ninjabank.security.entities.User;
import com.mb.ninjabank.security.repositories.RoleRepository;
import com.mb.ninjabank.security.repositories.UserRepository;
import com.mb.ninjabank.security.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@Import(SecurityConfiguration.class)
public class SecurityApplication
{
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class,args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(UserService userService, UserRepository userRepository, RoleRepository roleRepository) {
        return  args -> {
            userRepository.deleteAll();
            roleRepository.deleteAll();
           String [] roleNames = {"USER", "ADMIN", "CUSTOMER-MANGER", "FRAUD-MANGER", "TRANSACTION-MANAGER"};
            Set<Role> roles = Stream.of(roleNames).map(r -> {
                Role role = new Role();
                role.setRoleName(r);
                return userService.addRole(role);

            }).collect(Collectors.toSet());

            String [] userNames = {"bedril", "frank", "marius", "ateba"};
            final List<User> users = Stream.of(userNames).map(name -> {
                User user = new User();
                user.setUsername(name);
                user.setPassword("password");
                return userService.addUser(user);
            }).toList();

            for (int i = 0; i < users.size(); i++) {
                if(i==0 || i == 1){
                    userService.addRolesToUser(users.get(i).getUsername(), roleNames[i]);
                }
                else {
                    userService.addRolesToUser(users.get(i).getUsername(), roleNames[0], roleNames[i]);
                }
            }

            userService.listUsers().forEach(System.out::println);

        };
    }
}
