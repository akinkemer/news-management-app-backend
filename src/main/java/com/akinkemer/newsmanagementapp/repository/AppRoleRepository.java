package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.security.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    AppRole findByName(String name);
}
