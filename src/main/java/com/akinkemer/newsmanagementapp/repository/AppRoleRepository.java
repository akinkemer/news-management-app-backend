package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    Optional<AppRole> findByName(String name);
}
