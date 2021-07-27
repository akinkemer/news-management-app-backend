package com.akinkemer.newsmanagementapp.config;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@Configuration
public class AppUserConfig {
    @Bean
    CommandLineRunner commandLineRunner(AppUserService appUserService) {
        return args -> {
            appUserService.saveRole(new AppRole(null, "ROLE_ADMIN"));
            appUserService.saveRole(new AppRole(null, "ROLE_USER"));

            appUserService.saveUser(new AppUser(
                    null,
                    "Akın Kemer",
                    "akinkemer",
                    "12345",
                    new ArrayList<>()));

            appUserService.addRoleToUser("akinkemer","ROLE_ADMIN");

            appUserService.saveUser(new AppUser(
                    null,
                    "John Doe",
                    "johndoe",
                    "12345",
                    new ArrayList<>()));

            appUserService.addRoleToUser("johndoe","ROLE_USER");


        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
