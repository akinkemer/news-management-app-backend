package com.akinkemer.newsmanagementapp.config;

import com.akinkemer.newsmanagementapp.domain.app.Announcement;
import com.akinkemer.newsmanagementapp.domain.app.News;
import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import com.akinkemer.newsmanagementapp.repository.AnnouncementRepository;
import com.akinkemer.newsmanagementapp.repository.NewsRepository;
import com.akinkemer.newsmanagementapp.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Configuration
public class AppUserConfig {
    @Bean
    CommandLineRunner commandLineRunner(
            AppUserService appUserService,
            AnnouncementRepository announcementRepository,
            NewsRepository newsRepository) {
        return args -> {

            announcementRepository.save(new Announcement(
                    "Konu",
                    "İçerik",
                    LocalDate.now().plusDays(10),
                    "http://localhost:8080/api/v1/file/download/Ak%C4%B1n%20Kemer%20Kimlik.jpeg",
                    LocalDateTime.now()
                    ));
            newsRepository.save(new News(
                    "Konu",
                    "İçerik",
                    LocalDate.now().plusDays(10),
                    "https://www.aksam.com.tr/guncel/ormanin-kahramanlari/haber-1192734",
                    LocalDateTime.now()
            ));

            appUserService.saveRole(new AppRole(null, "ROLE_ADMIN"));
            appUserService.saveRole(new AppRole(null, "ROLE_USER"));

            appUserService.saveUser(new AppUser(
                    null,
                    "Akın Kemer",
                    "akinkemer",
                    "12345678",
                    "kemerakin@gmail.com",
                    new ArrayList<>()));

            appUserService.addRoleToUser("akinkemer", "ROLE_ADMIN");

            appUserService.saveUser(new AppUser(
                    null,
                    "John Doe",
                    "johndoe",
                    "12345678",
                    "johndoe@gmail.com",
                    new ArrayList<>()));

        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
