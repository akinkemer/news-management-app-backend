package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.security.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUserName(String userName);
}
