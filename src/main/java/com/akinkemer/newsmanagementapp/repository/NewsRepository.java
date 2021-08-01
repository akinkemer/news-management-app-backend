package com.akinkemer.newsmanagementapp.repository;

import com.akinkemer.newsmanagementapp.domain.app.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News,Long> {
    @Query("SELECT n FROM News n WHERE n.link IS NOT NULL")
    List<News> findAll();
}
