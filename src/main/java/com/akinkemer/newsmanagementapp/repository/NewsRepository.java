package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.app.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News,Long> {
}
