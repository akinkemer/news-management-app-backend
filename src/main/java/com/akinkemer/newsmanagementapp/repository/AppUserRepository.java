package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    Optional<AppUser> findByUserName(String userName);

}
